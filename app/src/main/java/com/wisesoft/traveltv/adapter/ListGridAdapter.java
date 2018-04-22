
package com.wisesoft.traveltv.adapter;

import android.content.Context;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

public class ListGridAdapter extends CommonRecyclerViewAdapter<ItemInfoBean> {
    public ListGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.list_new_design_item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemInfoBean item, int position) {
        helper.getHolder().setText(R.id.m_title_tv,item.getName())
                .showImage(R.id.m_recommend_iv,item.getImgUrl());
    }

}
