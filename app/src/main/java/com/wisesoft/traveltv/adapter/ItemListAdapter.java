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
 * Created by picher on 2017/10/18.
 * Describe：
 */

public class ItemListAdapter extends BasicAdapter<ItemInfoBean, RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD = 0x01;

    public ItemListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfoBean item = mDataList.get(position);
        if(holder instanceof ViewHolder){
            ViewHolder h = (ViewHolder) holder;
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(item.getImgUrl(), h.mRecommendIv);
            h.mTitleTv.setText(item.getName());
        }else{
            HeadHolder h = (HeadHolder) holder;
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(item.getImgUrl(), h.mHead1Iv);
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(mDataList.get(position+1).getImgUrl(), h.mHead2Iv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_stay, null));

        /*if(viewType ==TYPE_HEAD){
            return new HeadHolder(mInflater.inflate(R.layout.layout_stay_head, null));
        }else{
            return new ViewHolder(mInflater.inflate(R.layout.layout_stay, null));
        }*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.m_recommend_iv)
        ImageView mRecommendIv;
        @Bind(R.id.m_title_tv)
        TextView mTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class HeadHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.m_head1_iv)
        ImageView mHead1Iv;
        @Bind(R.id.m_head2_iv)
        ImageView mHead2Iv;

        public HeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
