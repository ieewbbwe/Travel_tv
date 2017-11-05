package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by picher on 2017/9/9.
 * Describe：二级栏目列表适配器
 */

public class ItemLinearAdapter extends BasicAdapter<ItemInfoBean, ItemLinearAdapter.ViewHolder> {

    public ItemLinearAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        ItemInfoBean itemInfoBean = mDataList.get(position);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(itemInfoBean.getImgUrl(), holder.mItemIv);
        holder.mTitleTv.setText(itemInfoBean.getName());
        holder.mScoreTv.setText(itemInfoBean.getGradeStr());
        holder.mIntroduceTv.setText(itemInfoBean.getIntroduce());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_list_item, null));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.m_item_iv)
        ImageView mItemIv;
        @Bind(R.id.m_title_tv)
        TextView mTitleTv;
        @Bind(R.id.m_score_tv)
        TextView mScoreTv;
        @Bind(R.id.m_introduce_tv)
        TextView mIntroduceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
