package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.FilterItemModel;

/**
 * Created by picher on 2018/4/19.
 * Describe：
 */

public class FilterNewDesignAdapter extends BasicAdapter<FilterItemModel,RecyclerView.ViewHolder> {

    private static final int FILTER_LAYOUT_TYPE_NORMAL = 0;

    @Override
    public int getItemViewType(int position) {
        FilterItemModel itemModel = mDataList.get(position);
        if(itemModel.getType() != null){
            switch (itemModel.getType()) {
                case TAB_SEARCH:
                case TAB_RECOMMEND:
                case TAB_PLAY:
                case TAB_EAT:
                case TAB_STAY:
                case TAB_PAY:
                case TAB_FUN:
                default:
                    return FILTER_LAYOUT_TYPE_NORMAL;
            }
        }else{
            return FILTER_LAYOUT_TYPE_NORMAL;

        }
    }

    public FilterNewDesignAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(RecyclerView.ViewHolder holder, int position) {
        FilterItemModel itemModel = mDataList.get(position);
        if(holder instanceof NormalViewHolder){
            //设置背景
            GradientDrawable dw = new GradientDrawable();
            dw.setCornerRadius(10f);
            dw.setColor(Color.parseColor("#29B590"));
            ((NormalViewHolder) holder).mRootFl.setBackground(dw);
            //设置信息
            ((NormalViewHolder) holder).mFilterTv.setText(itemModel.getFilterData().getName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case FILTER_LAYOUT_TYPE_NORMAL:
                default:
                return new NormalViewHolder(mInflater.inflate(R.layout.filter_new_design_item, parent,false));
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {

        FrameLayout mRootFl;
        TextView mFilterTv;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mRootFl = (FrameLayout) itemView.findViewById(R.id.m_filter_new_item_root_fl);
            mFilterTv = (TextView) itemView.findViewById(R.id.m_filter_new_item_tv);
        }
    }

}
