package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.Utiles;
import com.owen.tvrecyclerview.TwoWayLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.adapter.CommonRecyclerViewHolder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.ITitleAdapter;
import com.wisesoft.traveltv.layoutManager.CustomerGridlayoutManager;
import com.wisesoft.traveltv.manager.ConvertManager;
import com.wisesoft.traveltv.model.HotListItemModel;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by picher on 2018/4/28.
 * Describe：
 */

public class HotListAdapter extends CommonRecyclerViewAdapter<HotListItemModel> implements ITitleAdapter {
    private V7LinearLayoutManager mLinearManager;
    private OnItemClickListener onItemClickListener;

    public HotListAdapter(Context context) {
        super(context);
    }

    public interface OnItemClickListener {
        void onItemClick(ItemInfoBean infoBean);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.hot_list_item_layout;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, HotListItemModel item, int position) {
        helper.getHolder().setText(R.id.m_hot_list_item_tv, switchByType(item.getType()));
        TvRecyclerView mHotListTrv = helper.getHolder().getView(R.id.m_hot_list_item_trv);
        final SpannableAdapter spannableAdapter = new SpannableAdapter(mContext, mHotListTrv);
        //将HotItem转换为有具体宽高比的itemModel
        spannableAdapter.setDatas(ConvertManager.getInstance().convertItemToSpannable(item.getItemInfoBeans(), item.getType()));
        CustomerGridlayoutManager mLinearManager = new CustomerGridlayoutManager(mContext);
        mHotListTrv.setSpacingWithMargins(28, 28);
        mHotListTrv.setLayoutManager(mLinearManager);
        mHotListTrv.setAdapter(spannableAdapter);
        //设置List 宽高 行列等熟悉
        mLinearManager.setNumRows(item.getRowSize());
        mLinearManager.setNumColumns(item.getColumnSize());
        mLinearManager.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        mHotListTrv.getLayoutParams().height = Utiles.dip2px(mContext, item.getHeight());
        //设置点击事件
        spannableAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {

            }

            @Override
            public void onItemClick(View itemView, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(spannableAdapter.getItem(position).getItemInfoBean());
                }
            }
        });
    }

    private String switchByType(String type) {
        String title = "";
        switch (type) {
            case Constans.HOT_PAGE_TYPE_WEEK:
                title = "本周热门";
                break;
            case Constans.HOT_PAGE_TYPE_TODAY:
                title = "今日推荐";
                break;
            case Constans.HOT_PAGE_TYPE_PLAY:
                title = "最好玩的地方";
                break;
            case Constans.HOT_PAGE_TYPE_EAT:
                title = "最有人气的美食";
                break;
            case Constans.HOT_PAGE_TYPE_STAY:
                title = "精品酒店";
                break;
            case Constans.HOT_PAGE_TYPE_PAY:
                title = "热门购物";
                break;
            case Constans.HOT_PAGE_TYPE_FUN:
                title = "最受欢迎的娱乐天地";
                break;
        }
        return title;
    }

    @Override
    public View getTitleView(int index, RecyclerView parent) {
        /*switch (index) {
            case 1:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title2, parent, false);
            case 2:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title3, parent, false);
        }*/
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
    }
}
