package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.InitDataBean;

/**
 * Created by picher on 2017/9/24.
 * Describe：
 */

public class FilterAdapter extends BasicAdapter<InitDataBean, FilterAdapter.ViewHolder> {

    private int selectPos = 0;
    private int preSelectPos = 0;

    private InitDataBean parentFilter;

    public FilterAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        InitDataBean item = mDataList.get(position);
        holder.mFiterTv.setText(item.getName());
        holder.itemView.setTag(item);
        int color;
        if (position == selectPos) {
            color = Color.parseColor("#FF4081");
        }else{
            color = Color.parseColor("#ededed");
        }
        holder.mFiterTv.setTextColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_filter_text, parent, false));
    }

    public void setItemSelected(int childPos) {
        this.preSelectPos =selectPos;
        this.selectPos = childPos;
        notifyItemChanged(childPos);
        notifyItemChanged(preSelectPos);
    }

    public void setParentFilter(InitDataBean filterBean) {
        this.parentFilter = filterBean;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mFiterTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mFiterTv = (TextView) itemView.findViewById(R.id.m_filter_tv);
        }
    }
}
