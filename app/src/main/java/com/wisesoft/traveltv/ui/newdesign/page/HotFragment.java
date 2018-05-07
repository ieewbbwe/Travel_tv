package com.wisesoft.traveltv.ui.newdesign.page;

import android.content.ClipData;
import android.text.TextUtils;
import android.view.View;

import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.ui.newdesign.BaseNewDesignFragment;

import java.lang.reflect.Type;
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
        //防止空页面，从缓存加载本周热门和今日推荐的信息
        loadLocale(Constans.HOT_PAGE_TYPE_WEEK ,0);
        loadLocale(Constans.HOT_PAGE_TYPE_TODAY ,1);

        //获取推荐信息
        requestData(Constans.HOT_PAGE_TYPE_WEEK,9,1);
        requestData(Constans.HOT_PAGE_TYPE_TODAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_PLAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_STAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_EAT,8,1);
        requestData(Constans.HOT_PAGE_TYPE_PAY,8,1);
        requestData(Constans.HOT_PAGE_TYPE_FUN,8,1);
    }

    private void loadLocale(String key,int index) {
        String cacheStr = SharedPrefManager.getString(key,"");
        if(!TextUtils.isEmpty(cacheStr)){
            Type type = new TypeToken<List<ItemInfoBean>>(){}.getType();
            List<ItemInfoBean> itemInfoBean = new Gson().fromJson(cacheStr,type);
            if(!CollectionUtils.isEmpty(itemInfoBean) && mHotListItemDatas != null){
                mHotListItemDatas.add(index,ConvertManager.getInstance().convertItemToHotItem(itemInfoBean, key));
                mHotListAdapter.notifyDataSetChanged();
            }
        }
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
                    mHotListItemDatas.set(0,ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }else if(Constans.HOT_PAGE_TYPE_TODAY.equals(type)){
                    mHotListItemDatas.remove(1);
                    mHotListItemDatas.set(1,ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }else{
                    mHotListItemDatas.add(ConvertManager.getInstance().convertItemToHotItem(itemInfoBeans, type));
                }
                SharedPrefManager.putString(type, new Gson().toJson(itemInfoBeans));
                mHotListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void requestFocus() {
        mHotTrv.requestFocus();
    }
}
