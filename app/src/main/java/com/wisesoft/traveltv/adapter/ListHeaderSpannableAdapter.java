package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.ItemTypeModel;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

public class ListHeaderSpannableAdapter extends CommonRecyclerViewAdapter<ItemTypeModel> {
    private RecyclerView mRecyclerView;
    
    public ListHeaderSpannableAdapter(Context context, RecyclerView recyclerView) {
        super(context);
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.header_new_design_play_item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemTypeModel item, int position) {
        ItemInfoBean itemInfo = item.getFilterData();
        helper.getHolder()
                .setText(R.id.m_title_tv,itemInfo.getName())
                .showImage(R.id.m_item_iv, itemInfo.getImgUrl());

        final View itemView = helper.itemView;
        final boolean isVertical = mRecyclerView.getLayoutManager().canScrollVertically();

        if(itemView.getLayoutParams() != null && itemView.getLayoutParams() instanceof SpannableGridLayoutManager.LayoutParams){
            final SpannableGridLayoutManager.LayoutParams lp =
                    (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            //默认一个item 占用1列1行
            int span1 = 1;
            int span2 = 1;

            //根据类型不同切换布局
            switch (item.getType()){
                case TAB_PLAY:
                    span1 = position == 0  ? 2 : 1;
                    span2 = position == 0  ? 1 : 1;
                    break;
                case TAB_EAT:
                    span1 = position == 0  ? 1 : 1;
                    span2 = position == 0 || position == 1 || position == 2 ? 2 : 1;
                    break;
                case TAB_STAY:
                    span1 = position == 0  ? 1 : 1;
                    if(position == 0){
                        span2 = 3;
                    }else if(position == 4 || position == 5 || position == 6){
                        span2 = 2;
                    }else {
                        span1 = 1;
                    }
                    break;
                case TAB_PAY:
                case TAB_FUN:
                    break;
            }

            final int colSpan = (isVertical ? span2 : span1);
            final int rowSpan = (isVertical ? span1 : span2);
            if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
                lp.rowSpan = rowSpan;
                lp.colSpan = colSpan;

                itemView.setLayoutParams(lp);
            }
        }
    }
}
