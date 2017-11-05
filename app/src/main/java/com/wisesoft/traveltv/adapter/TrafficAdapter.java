package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.bumptech.glide.Glide;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017/9/22.
 * Describe：
 */

public class TrafficAdapter extends BasicAdapter<ItemInfoBean, TrafficAdapter.ViewHolder> {

    private String[] titles = {"飞机/机票", " 火车/动车/高铁", "轮渡/游轮","大巴/自驾/租车"};

    public TrafficAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        ItemInfoBean infoBean = mDataList.get(position);
        Glide.with(mContext).load(infoBean.getImgUrl()).crossFade()
                .into(holder.mTrafficIv);
        holder.mTitleTv.setText(titles[position]);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_traffic, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.m_traffic_iv)
        ImageView mTrafficIv;
        @Bind(R.id.m_title_tv)
        TextView mTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
