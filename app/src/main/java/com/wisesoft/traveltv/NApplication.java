package com.wisesoft.traveltv;

import com.android_mobile.core.base.BaseApplication;
import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.manager.image.ImageLoadFactory;

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
    }
}
