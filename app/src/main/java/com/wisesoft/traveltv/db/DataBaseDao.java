package com.wisesoft.traveltv.db;

import android.content.Context;

import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ImageBean;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.model.VideoBean;

import java.sql.SQLException;
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

    public DataBaseDao(Context context) {
        try {
            mHelper = DataBaseHelper.getInstance(context, DB_VERSION_CODE);
            mItemDao = mHelper.getDao(ItemInfoBean.class);
            mVideoDao = mHelper.getDao(VideoBean.class);
            mImageDao = mHelper.getDao(ImageBean.class);
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
                    return null;
                }
            });
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

    public List<ItemInfoBean> getItemInfos(String typeEat) {
        List<ItemInfoBean> items = null;
        try {
            items = mItemDao.queryBuilder().where().eq("type", typeEat).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

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

    public List<ItemInfoBean> getItemInfos(long count) {
        List<ItemInfoBean> beanList = null;
        try {
            beanList = mItemDao.queryBuilder().orderBy("view_count", false).limit(count).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beanList;
    }
}
