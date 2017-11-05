package com.wisesoft.traveltv.helper;

import android.content.Context;
import android.widget.ImageView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by picher on 2017/9/20.
 * Describe：
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object obj, ImageView imageView) {
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(((ImageBean)obj).getImgUrl(),imageView);
    }
}
