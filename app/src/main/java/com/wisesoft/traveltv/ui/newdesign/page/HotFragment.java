package com.wisesoft.traveltv.ui.newdesign.page;

import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.HotListAdapter;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.ui.newdesign.BaseNewDesignFragment;

/**
 * Created by picher on 2018/4/27.
 * Describe：热门推荐
 */

public class HotFragment extends BaseNewDesignFragment{

    private TvRecyclerView mHotTrv;
    private HotListAdapter mHotListAdapter;
    private V7LinearLayoutManager mLayoutManager;

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
        mHotListAdapter.setDatas(DataEngine.getTestHotList());
        mHotTrv.setSpacingWithMargins(12,12);
        mHotTrv.setSelectedItemAtCentered(true);

        mHotTrv.setAdapter(mHotListAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void requestFocus() {
        mHotTrv.requestFocus();
    }
}
