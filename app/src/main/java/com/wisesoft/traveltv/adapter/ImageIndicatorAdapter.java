package com.wisesoft.traveltv.adapter;

import android.content.Context;

import com.android_mobile.core.BasicAdapter;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by picher on 2018/5/6.
 * Describe：
 */

public class ImageIndicatorAdapter extends CommonRecyclerViewAdapter<ItemInfoBean> {

    public ImageIndicatorAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.image_indicator_item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemInfoBean item, int position) {
        helper.getHolder().showImage(R.id.m_item_iv,item.getImgUrl());
    }
}
