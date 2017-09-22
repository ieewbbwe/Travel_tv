package com.wisesoft.traveltv.ui.traffic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android_mobile.core.utiles.Lg;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.TrafficAdapter;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVIconView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-行
 */
public class TrafficActivity extends NActivity {

    @Bind(R.id.m_eat_tiv)
    TVIconView mEatTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_sort_tiv)
    TVIconView mSortTiv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_list_rlv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
    @Bind(R.id.m_introduce_tv)
    TextView mIntroduceTv;

    private List<ItemInfoBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);

        TrafficAdapter mAdapter = new TrafficAdapter(this);
        mAdapter.setDataList(DataEngine.getTrafficInfo());
        mListRlv.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        mListRlv.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {
                Lg.d("picher", "onItemPreSelected：" + position);
            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                Lg.d("picher", "onItemSelected：" + position);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Lg.d("picher", "onItemClick：" + position);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
