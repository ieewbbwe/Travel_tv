package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by mxh on 2017/9/15.
 * Describe：
 */

public class StaggeredAdapter extends BasicAdapter<ItemInfoBean, StaggeredAdapter.ViewHolder> {

    public StaggeredAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        View itemView = holder.itemView;
        ItemInfoBean item = mDataList.get(position);
        holder.mTitleTv.setText(item.getName());
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(item.getImgUrl(), holder.mItemIv);

        int dimenId;
        if (position % 3 == 0) {
            dimenId = 400;
        } else if (position % 5 == 0) {
            dimenId = 310;
        } else if (position % 7 == 0) {
            dimenId = 360;
        } else {
            dimenId = 210;
        }

        int size = dimenId;
        StaggeredGridLayoutManager.LayoutParams lp =
                (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        lp.height = size;
        itemView.setLayoutParams(lp);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_staggered, parent, false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemIv;
        private TextView mTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemIv = (ImageView) itemView.findViewById(R.id.m_item_iv);
            mTitleTv = (TextView) itemView.findViewById(R.id.m_title_tv);
        }
    }
}
