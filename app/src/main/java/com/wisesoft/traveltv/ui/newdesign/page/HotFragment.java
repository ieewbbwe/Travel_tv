package com.wisesoft.traveltv.ui.newdesign.page;

import android.view.View;

import com.android_mobile.core.utiles.Lg;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.HotListAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.OnItemInfoLoadListener;
import com.wisesoft.traveltv.manager.ConvertManager;
import com.wisesoft.traveltv.model.HotListItemModel;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.newdesign.BaseNewDesignFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * Created by picher on 2018/4/27.
 * Describe：热门推荐
 */

public class HotFragment extends BaseNewDesignFragment{

    private TvRecyclerView mHotTrv;
    private HotListAdapter mHotListAdapter;
    private V7LinearLayoutManager mLayoutManager;
    private String[] hotTypes = {Constans.TYPE_PLAY,Constans.TYPE_EAT,Constans.TYPE_STAY,Constans.TYPE_PAY,Constans.TYPE_FUN};
    private List<HotListItemModel> mHotListItemDatas = new ArrayList<>();

    @Override
    protected int create() {
        return R.layout.hot_fragment_layout;
    }

    @Override
    protected void initComp() {
        mHotTrv = (TvRecyclerView) findViewById(R.id.m_hot_trv);
        mLayoutManager = new V7LinearLayoutManager(getContext());
        mHotTrv.setLayoutManager(mLayoutManager);
        mHotListAdapter = new HotListAdapter(getContext());
        /*-----測試數據-----*/
        //mHotListItemDatas.addAll(DataEngine.getTestHotList());
        /*------------------*/
        mHotListAdapter.setDatas(mHotListItemDatas);
        mHotTrv.setSpacingWithMargins(12,12);
        mHotTrv.setSelectedItemAtCentered(true);

        mHotTrv.setAdapter(mHotListAdapter);
    }

    @Override
    protected void initListener() {
        mHotListAdapter.setOnItemClickListener(new HotListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemInfoBean infoBean) {
                jumpToDetail(infoBean);
            }
        });
    }

    @Override
    protected void initData() {
        //获取推荐信息
        requestData(Constans.HOT_PAGE_TYPE_WEEK,9,1);
        requestData(Constans.HOT_PAGE_TYPE_TODAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_PLAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_STAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_EAT,8,1);
        requestData(Constans.HOT_PAGE_TYPE_PAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_FUN,8,1);
    }

    private String getWeekRecommendType() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        int weekNum = cal.get(Calendar.WEEK_OF_YEAR);
        String type;
        if(weekNum >= hotTypes.length){
            type = hotTypes[weekNum % hotTypes.length];
        }else{
            type = hotTypes[weekNum];
        }
        return type;
    }

    private void requestData(final String type, int size, int page) {
        String requestType = "";
        switch (type){
            case Constans.HOT_PAGE_TYPE_WEEK:
                requestType = getWeekRecommendType();
                break;
            case Constans.HOT_PAGE_TYPE_TODAY:
                requestType = "";
                break;
            case Constans.HOT_PAGE_TYPE_PLAY:
                requestType = Constans.TYPE_PLAY;
                break;
            case Constans.HOT_PAGE_TYPE_EAT:
                requestType = Constans.TYPE_EAT;
                break;
            case Constans.HOT_PAGE_TYPE_STAY:
                requestType = Constans.TYPE_STAY;
                break;
            case Constans.HOT_PAGE_TYPE_PAY:
                requestType = Constans.TYPE_PAY;
                break;
            case Constans.HOT_PAGE_TYPE_FUN:
                requestType = Constans.TYPE_FUN;
                break;

        }
        getRecommendData(requestType, size, page,new OnItemInfoLoadListener() {
            @Override
            public void onLoadSucceed(List<ItemInfoBean> itemInfoBeans) {
                if(Constans.HOT_PAGE_TYPE_WEEK.equals(type)){
                    mHotListItemDatas.add(0,ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }else if(Constans.HOT_PAGE_TYPE_TODAY.equals(type)){
                    mHotListItemDatas.add(1,ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }else{
                    mHotListItemDatas.add(ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }
                mHotListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void requestFocus() {
        mHotTrv.requestFocus();
    }
}
