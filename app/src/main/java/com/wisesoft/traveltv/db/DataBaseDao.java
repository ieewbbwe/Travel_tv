package com.wisesoft.traveltv.db;

import android.content.Context;

import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.OnWorkListener;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.model.temp.VideoBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by mxh on 2017/9/21.
 * Describe：
 */

public class DataBaseDao {

    private static final int DB_VERSION_CODE = 1;
    private DataBaseHelper mHelper;
    private Dao<VideoBean, Integer> mVideoDao;
    private Dao<ImageBean, Integer> mImageDao;
    private Dao<ItemInfoBean, Integer> mItemDao;
    private Dao<InitDataBean, Integer> mInitData;

    public DataBaseDao(Context context) {
        try {
            mHelper = DataBaseHelper.getInstance(context, DB_VERSION_CODE);
            mItemDao = mHelper.getDao(ItemInfoBean.class);
            mVideoDao = mHelper.getDao(VideoBean.class);
            mImageDao = mHelper.getDao(ImageBean.class);
            mInitData = mHelper.getDao(InitDataBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化一批数据
     */
    public void initDatabase() {
        try {
            TransactionManager.callInTransaction(mHelper.getConnectionSource(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    updateImage();
                    // updateVideo();
                    updateItemInfo();
                    SharedPrefManager.putBoolean(Constans.IS_INIT_DATA, true);

                    //逻辑修改，菜肴图片与酒店关联
                    updateHotelImg();
                    updateHotelItemInfo();
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDatabase(final List<InitDataBean> dataBeen) {
        initDatabase(dataBeen,null);
    }

    public void initDatabase(final List<InitDataBean> dataBeen, OnWorkListener onWorkListener) {
        if (CollectionUtils.isEmpty(dataBeen)) {
            if(onWorkListener != null){
                onWorkListener.onComplete();
            }
            return;
        }
        try {
            TransactionManager.callInTransaction(mHelper.getConnectionSource(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    updateInitData(dataBeen);
                    //缓存最后更新的时间 做版本增量，在这时间之后若有修改则初始化数据库
                    SharedPrefManager.putString(Constans.CACHE_INIT_UPDATE_TIME, mInitData.queryBuilder()
                            .orderBy("update_time", false).queryForFirst().getUpdate_time());
                    return null;
                }
            });
            if(onWorkListener != null){
                onWorkListener.onComplete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInitData(List<InitDataBean> dataBeen) throws SQLException {
        //暂时不做假删
        //mInitData.deleteBuilder().delete();
        for (InitDataBean item : dataBeen) {
            InitDataBean query = mInitData.queryBuilder().where().eq("id", item.getId()).queryForFirst();
            if (query != null) {
                query = item;
                mInitData.update(query);
            } else {
                mInitData.create(item);
            }
        }
    }

    private void updateHotelItemInfo() {
        try {
            List<ItemInfoBean> beanList = DataEngine.getHotelFoodInfos();
            for (ItemInfoBean item : beanList) {
                mItemDao.create(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateItemInfo() {
        try {
            List<ItemInfoBean> beanList = DataEngine.getItemInfos();
            Lg.print("picher", "条目:" + beanList.size());
            for (ItemInfoBean item : beanList) {
                mItemDao.create(item);
                mItemDao.create(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateVideo() {

    }

    private void updateImage() {
        try {
            List<ImageBean> beanList = DataEngine.getImages();
            for (ImageBean item : beanList) {
                mImageDao.create(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateHotelImg() {
        try {
            List<ImageBean> beanList = DataEngine.getHotelImages();
            for (ImageBean item : beanList) {
                mImageDao.create(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<ItemInfoBean> getItemInfos() {
        return getItemInfos("", -1);
    }

    public List<ItemInfoBean> getItemInfos(String typeEat) {
        return getItemInfos(typeEat, -1);
    }

    public List<ItemInfoBean> getItemInfos(long limit) {
        return getItemInfos("", limit);
    }

    public List<ItemInfoBean> getItemInfos(String type, long limit) {
        List<ItemInfoBean> items = null;
        try {
            QueryBuilder<ItemInfoBean, Integer> builder = mItemDao.queryBuilder();
            if (!"".equals(type)) {
                builder.where().eq("type_str", type);
            }
            if (limit > 0) {
                builder.limit(limit);
            }
            items = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ItemInfoBean> getHotItemInfos(long count) {
        List<ItemInfoBean> beanList = null;
        try {
            beanList = mItemDao.queryBuilder().orderBy("view_count", false).limit(count).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    //根据order查找图片
    public ImageBean queryImageByOrder(int order, String type) {
        List<ImageBean> imgs = null;
        try {
            imgs = mImageDao.queryBuilder().where().eq("img_order", order)
                    .and().eq("type", type).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CollectionUtils.isNotEmpty(imgs) ? imgs.get(0) : null;
    }

    //按照酒店id查找相关图片
    public List<ItemInfoBean> queryItemInfoByHotelId(String hotel_id) {
        List<ItemInfoBean> infoBeen = null;
        try {
            infoBeen = mItemDao.queryBuilder().where().eq("type", Constans.TYPE_HOTEL_FOOD).and()
                    .eq("hotel_id", hotel_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infoBeen;
    }

    public List<ImageBean> queryImageByHotelOrder(String hotel_id) {
        List<ImageBean> imageBeen = null;
        try {
            imageBeen = mImageDao.queryBuilder().where().eq("type", Constans.TYPE_HOTEL_FOOD)
                    .and().eq("hotel_id", hotel_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageBeen;
    }

    public ItemInfoBean getItemInfoByName(String name) {
        try {
            return mItemDao.queryBuilder().where().like("name", "%" + name + "%").queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ItemInfoBean> getRecommendInfo(String typePlay) {
        return getItemInfos(typePlay, 5);
    }

    public List<InitDataBean> getNewDesignFilter(String mPageType){
        try {
            List<InitDataBean> filterAll = new ArrayList<>();
            switch (mPageType){
                case Constans.TYPE_PLAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));//区域
                    break;
                case Constans.TYPE_EAT:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_FOOD_TYPE));//酒店类型
                    break;
                case Constans.TYPE_STAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_STAR));//星级
                    break;
                case Constans.TYPE_PAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_PAY_TYPE));//购物类型
                    break;
                case Constans.TYPE_FUN:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_FUN_TYPE));//娱乐类型
                    break;
            }

            return filterAll;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //旧版本获取筛选数据
    public List<InitDataBean> getPageFilter(String mPageType) {
        try {
            List<InitDataBean> filterAll = new ArrayList<>();
            switch (mPageType) {
                case Constans.TYPE_EAT:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_FOOD_TYPE));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_PRICE));
                    break;
                case Constans.TYPE_PLAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_STAR));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_SLIGHT));
                    break;
                case Constans.TYPE_STAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_STAR));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_PRICE));
                    break;
                case Constans.TYPE_PAY:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_STAR));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_PRICE));
                    break;
                case Constans.TYPE_FUN:
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_AREA));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_STAR));
                    filterAll.add(findFilterData(Constans.FILTER_DATABASE_PRICE));
                    break;
            }
            return filterAll;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找筛选参数
     * @return 地区参数
     * @throws SQLException
     */
    private InitDataBean findFilterData(String filterParentId) throws SQLException {
        InitDataBean filterBean = mInitData.queryBuilder().where().eq("id_core", filterParentId).queryForFirst();
        if (filterBean != null) {
            List<InitDataBean> child = mInitData.queryBuilder().where().eq("parent_id", filterParentId).query();
            child.add(0,new InitDataBean("","全部"));
            filterBean.setChildBean(child);
        }
        return filterBean;
    }

    private void addAreaPrice(List<InitDataBean> filterAll) throws SQLException {
        InitDataBean filterBean;
        filterBean = mInitData.queryBuilder().where().eq("id_core", "003002").queryForFirst();
        if (filterBean != null) {
            List<InitDataBean> child = mInitData.queryBuilder().where().eq("parent_id", "003002").query();
            child.add(0,new InitDataBean("0","全部"));
            filterBean.setChildBean(child);
            filterAll.add(filterBean);
        }
        filterBean = mInitData.queryBuilder().where().eq("id_core", "003003").queryForFirst();
        if (filterBean != null) {
            List<InitDataBean> child = mInitData.queryBuilder().where().eq("parent_id", "003003").query();
            child.add(0,new InitDataBean("0","全部"));
            filterBean.setChildBean(child);
            filterAll.add(filterBean);
        }
    }

    public String getPriceStr(String price_id) {
        InitDataBean item = null;
        try {
            item = mInitData.queryBuilder().where().eq("id",price_id).queryForFirst();
            if(item != null){
                return item.getName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
