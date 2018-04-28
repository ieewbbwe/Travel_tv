/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.SpannableItemModel;

public class SpannableAdapter extends CommonRecyclerViewAdapter<SpannableItemModel> {
    private RecyclerView mRecyclerView;
    
    public SpannableAdapter(Context context, RecyclerView recyclerView) {
        super(context);
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.hot_item_layout;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, SpannableItemModel item, int position) {
        helper.getHolder()
                .setText(R.id.m_title_tv, String.valueOf(position))
                .showImage(R.id.m_item_iv, item.getItemInfoBean().getImgUrl());

        final View itemView = helper.itemView;
        final boolean isVertical = mRecyclerView.getLayoutManager().canScrollVertically();

        if(itemView.getLayoutParams() instanceof SpannableGridLayoutManager.LayoutParams){
            final SpannableGridLayoutManager.LayoutParams lp =
                    (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();

            final int span1 = item.getColSpan();
            final int span2 = item.getRowSpan();

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
