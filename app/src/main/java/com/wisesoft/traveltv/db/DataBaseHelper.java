package com.wisesoft.traveltv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android_mobile.core.utiles.Lg;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.model.temp.VideoBean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mxh on 2017/9/21.
 * Describe：
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static DataBaseHelper mInstance;
    private static final String DB_NAME = "travel.db";
    private static Map<String, Dao> daos = new HashMap<>();

    private DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DataBaseHelper getInstance(Context context, int version) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(context, DB_NAME, null, version);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, VideoBean.class);
            TableUtils.createTable(connectionSource, ItemInfoBean.class);
            TableUtils.createTable(connectionSource, ImageBean.class);
            TableUtils.createTable(connectionSource, InitDataBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Lg.print("database_update", "数据库版本更新");
    }

    @Override
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String simpleName = clazz.getSimpleName();

        if (daos.get(simpleName) == null) {
            dao = super.getDao(clazz);
            daos.put(simpleName, dao);
        } else {
            dao = daos.get(simpleName);
        }

        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (Dao dao : daos.values()) {
            dao = null;
        }
    }
}
