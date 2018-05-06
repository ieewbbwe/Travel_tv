package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.Lg;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017/9/14.
 * Describe：
 */

public class StayAdapter extends BasicAdapter<ItemInfoBean, StayAdapter.ViewHolder> {

    public StayAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        ItemInfoBean item = mDataList.get(position);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(item.getImgUrl(), holder.mRecommendIv);
        Lg.d("picher",""+item.getImgUrl());
        holder.mTitleTv.setText(item.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_stay, null));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.m_recommend_iv)
        ImageView mRecommendIv;
        @Bind(R.id.m_title_tv)
        TextView mTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
