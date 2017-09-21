package com.wisesoft.traveltv.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
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
                    updateVideo();
                    updateItemInfo();
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateItemInfo() {

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

    public List<ItemInfoBean> getItemInfos() {
        return null;
    }

}
