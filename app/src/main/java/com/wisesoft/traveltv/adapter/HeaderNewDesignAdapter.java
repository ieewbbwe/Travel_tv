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
import com.wisesoft.traveltv.model.FilterItemModel;
import com.wisesoft.traveltv.model.HeaderItemModel;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import butterknife.Bind;

/**
 * Created by picher on 2018/4/22.
 * Describe：
 */

public class HeaderNewDesignAdapter extends BasicAdapter<HeaderItemModel,RecyclerView.ViewHolder> {

    private static final int HEADER_LAYOUT_TYPE_PLAY = 1;

    @Override
    public int getItemViewType(int position) {
        HeaderItemModel itemModel = mDataList.get(position);
        switch (itemModel.getType()) {
            case TAB_SEARCH:
            case TAB_RECOMMEND:
            case TAB_PLAY:
            case TAB_EAT:
            case TAB_STAY:
            case TAB_PAY:
            case TAB_FUN:
            default:
                return HEADER_LAYOUT_TYPE_PLAY;
        }

    }

    public HeaderNewDesignAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfoBean itemModel = mDataList.get(position).getFilterData();
        if(itemModel != null){
            if(holder instanceof PlayHeaderHolder){
                ((PlayHeaderHolder) holder).mTitleTv.setText(itemModel.getName());
                ImageLoadFactory.getInstance().getImageLoadHandler()
                        .displayImage(itemModel.getImgUrl(),((PlayHeaderHolder) holder).mItemIv);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEADER_LAYOUT_TYPE_PLAY:
            default:
                return new PlayHeaderHolder(mInflater.inflate(R.layout.header_new_design_play_item, parent,false));
        }
    }

    public class PlayHeaderHolder extends RecyclerView.ViewHolder{
        ImageView mItemIv;
        TextView mTitleTv;
        public PlayHeaderHolder(View itemView) {
            super(itemView);
            mItemIv = (ImageView) itemView.findViewById(R.id.m_item_iv);
            mTitleTv = (TextView) itemView.findViewById(R.id.m_title_tv);

        }
    }
}
