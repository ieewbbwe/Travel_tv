package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android_mobile.core.BasicAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.ItemInfoBean;

/**
 * Created by mxh on 2017/9/22.
 * Describe：
 */

public class TrafficAdapter extends BasicAdapter<ItemInfoBean, TrafficAdapter.ViewHolder> {

    public TrafficAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_traffic, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
