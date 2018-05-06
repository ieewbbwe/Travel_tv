package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by picher on 2018/5/6.
 * Describe：
 */

public class ImageDetailAdapter extends PagerAdapter {

    private List<ItemInfoBean> imageBeans;
    private SparseArray<ImageView> mViews = new SparseArray<>();
    private Context context;

    public ImageDetailAdapter() {
    }

    public ImageDetailAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ItemInfoBean> datas){
        imageBeans = datas;
    }

    @Override
    public int getCount() {
        return imageBeans == null ? 0:imageBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemInfoBean item = imageBeans.get(position);
        if(mViews.get(position) == null){
            mViews.put(position, createImageView(item.getImgUrl()));
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    private ImageView createImageView(String imgUrl) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoadFactory.getInstance().getImageLoadHandler().displayImage(imgUrl,imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
