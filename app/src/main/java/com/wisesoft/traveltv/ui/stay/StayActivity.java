package com.wisesoft.traveltv.ui.stay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.StaggeredAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.ui.play.AmusementDetailActivity;
import com.wisesoft.traveltv.ui.view.TVIconView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-住
 */
public class StayActivity extends NActivity {

    @Bind(R.id.m_eat_tiv)
    TVIconView mEatTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_sort_tiv)
    TVIconView mSortTiv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_content_trv)
    TvRecyclerView mContentTrv;
    private StaggeredAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);
    }

    @Override
    protected void initComp() {
        initBorder();
        ButterKnife.bind(this);
        mContentTrv.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        mContentTrv.setSpacingWithMargins(15, 15);
        mAdapter = new StaggeredAdapter(this);
        mAdapter.setDataList(DataEngine.getVideos(60));
        mContentTrv.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mContentTrv.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                super.onItemSelected(parent, itemView, position);
                mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                super.onItemClick(parent, itemView, position);
                Intent intent = new Intent(mContext, AmusementDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, mAdapter.getDataList().get(position));
                pushActivity(intent, false);
            }
        });

        mContentTrv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });
        mReturnTiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popActivity();
            }
        });

    }

    @Override
    protected void initData() {

    }
}
