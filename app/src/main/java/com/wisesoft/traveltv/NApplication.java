package com.wisesoft.traveltv;

import android.content.Context;

import com.android_mobile.core.base.BaseApplication;
import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wisesoft.traveltv.model.DataEngine;

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
    }

    public static RefWatcher getRefWatcher(Context context) {
        return ((NApplication) context.getApplicationContext()).refWatcher;
    }
}
