package com.wisesoft.traveltv;

import android.content.Context;

import com.android_mobile.core.base.BaseApplication;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.OkHttpFactory;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wisesoft.traveltv.model.temp.DataEngine;

import java.io.InputStream;

/**
 * Created by mxh on 2017/8/8.
 * Describe：
 */

public class NApplication extends BaseApplication {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        //为了演示时填充数据
        DataEngine.init(getApplicationContext());
        //OkHttpFactory.init(getExternalCacheDir(), (InputStream[]) null);
    }

    public static RefWatcher getRefWatcher(Context context) {
        return ((NApplication) context.getApplicationContext()).refWatcher;
    }
}
