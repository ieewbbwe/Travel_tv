package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_mobile.core.BasicAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.KeyBoardItemBean;

/**
 * Created by picher on 2018/4/4.
 * Describe：
 */

public class KeyBoardAdapter extends BasicAdapter<KeyBoardItemBean,KeyBoardAdapter.ViewHolder> {

    public KeyBoardAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindItemHolder(ViewHolder holder, int position) {
        KeyBoardItemBean itemBean = mDataList.get(position);
        holder.softKey.setText(itemBean.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_key_board,parent,false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView softKey;

        public ViewHolder(View itemView) {
            super(itemView);
            softKey = (TextView) itemView.findViewById(R.id.m_key_board_tv);
        }
    }
}
