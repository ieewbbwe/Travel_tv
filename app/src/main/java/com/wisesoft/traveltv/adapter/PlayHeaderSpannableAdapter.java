
package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.ItemTypeModel;

public class PlayHeaderSpannableAdapter extends CommonRecyclerViewAdapter<ItemTypeModel> {
    private RecyclerView mRecyclerView;

    public PlayHeaderSpannableAdapter(Context context, RecyclerView recyclerView) {
        super(context);
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.header_new_design_play_item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemTypeModel item, int position) {
       /* helper.getHolder()
                .setText(R.id.title, String.valueOf(position))
                .showImage(R.id.image, item.imgUrl);*/

        final View itemView = helper.itemView;
        final boolean isVertical = mRecyclerView.getLayoutManager().canScrollVertically();

        final SpannableGridLayoutManager.LayoutParams lp =
                (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        final int span1 = (position == 0 || position == 6 || position == 13 || position == 5 ? 2 : 1);
        final int span2 = (position == 0 || position == 6 || position == 13 ? 2 : position == 5 ? 4 : 1);

        final int colSpan = (isVertical ? span2 : span1);
        final int rowSpan = (isVertical ? span1 : span2);
        if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
            lp.rowSpan = rowSpan;
            lp.colSpan = colSpan;

            itemView.setLayoutParams(lp);
        }
    }
}
