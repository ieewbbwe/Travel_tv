package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.view.View;

import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.ItemTypeModel;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

public class MetroAdapter extends CommonRecyclerViewAdapter<ItemTypeModel>{
    public MetroAdapter(Context context) {
        super(context);
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
        MetroGridLayoutManager.LayoutParams lp = (MetroGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        /*if(position > 18){
            lp.sectionIndex = 2;
            lp.rowSpan = 3;
            lp.colSpan = 2;
        }
        else if(position > 6) {
            lp.sectionIndex = 1;
            lp.isSuportIntelligentScrollEnd = false;
            lp.isSuportIntelligentScrollStart = true;
            if(position < 10) {
                lp.rowSpan = 15;
                lp.colSpan = 20;
            } else if(position < 14) {
                lp.rowSpan = 9;
                lp.colSpan = 15;
            } else {
                lp.rowSpan = 7;
                lp.colSpan = 12;
            }
        } else {
            lp.sectionIndex = 0;
            if(position == 0 || position == 6) {
                lp.rowSpan = 3;
                lp.colSpan = 4;
            } else {
                lp.rowSpan = 6;
                lp.colSpan = 4;
            }
        }*/
        itemView.setLayoutParams(lp);
    }

}
