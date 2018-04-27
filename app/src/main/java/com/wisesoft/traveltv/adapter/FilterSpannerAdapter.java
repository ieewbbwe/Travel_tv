package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.FilterItemModel;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * Created by picher on 2018/4/19.
 * Describe：
 */

public class FilterSpannerAdapter extends BasicAdapter<FilterItemModel,RecyclerView.ViewHolder> {

    private static final int FILTER_LAYOUT_TYPE_NORMAL = 0;
    private boolean isShowAnim = false;
    private boolean isBindListener = true;
    private CommonRecyclerViewAdapter.OnItemListener mOnItemListener;

    public void setOnItemListener(CommonRecyclerViewAdapter.OnItemListener listener) {
        mOnItemListener = listener;
    }

    private void onBindItemListener(final RecyclerView.ViewHolder holder){
        // 设置item的选择与点击监听
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                v.setSelected(hasFocus);
                if (hasFocus) {
                    if (isShowAnim) {
                        v.animate().scaleX(1.10f).scaleY(1.10f).setDuration(130).start();
                    }

                    int pos = holder.getLayoutPosition();
                    if (null != holder.itemView.getParent() && holder.itemView.getParent() instanceof RecyclerView) {
                        ((RecyclerView) holder.itemView.getParent()).smoothScrollToPosition(pos);
                    }
                    if (null != mOnItemListener) {
                        mOnItemListener.onItemSelected(holder.itemView, pos);
                    }
                } else {
                    if (isShowAnim) {
                        v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(130).start();
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemListener) {
                    int pos = holder.getLayoutPosition();
                    try {
                        mOnItemListener.onItemClick(holder.itemView, pos);
                    } catch (UndeclaredThrowableException E) {
                        E.printStackTrace();
                    }
                }
            }
        });
    }

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

    public FilterSpannerAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(RecyclerView.ViewHolder holder, int position) {
        if(isBindListener) {
            onBindItemListener(holder);
        }

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
