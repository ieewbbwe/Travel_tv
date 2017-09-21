package com.wisesoft.traveltv;

import com.android_mobile.core.base.BaseApplication;
import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.model.DataEngine;

/**
 * Created by mxh on 2017/8/8.
 * Describe：
 */

public class NApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadFactory.init(getApplicationContext());
        SharedPrefManager.init(getApplicationContext());

        //为了演示时填充数据
        DataEngine.init(getApplicationContext());
    }
}
