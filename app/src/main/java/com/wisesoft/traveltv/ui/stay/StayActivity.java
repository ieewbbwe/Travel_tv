package com.wisesoft.traveltv.ui.stay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.NApplication;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.StaggeredAdapter;
import com.wisesoft.traveltv.adapter.StayAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.FilterBean;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.play.AmusementDetailActivity;
import com.wisesoft.traveltv.ui.view.TVIconView;
import com.wisesoft.traveltv.ui.view.weight.pop.TVFilterView;

import java.util.Collections;
import java.util.List;

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
 /*   @Bind(R.id.m_sort_tiv)
    TVIconView mSortTiv;*/
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_content_trv)
    TvRecyclerView mContentTrv;
    @Bind(R.id.m_filter_tfv)
    TVFilterView mFilterTfv;

    private StayAdapter mAdapter;
    private List<ItemInfoBean> items;
    private DataBaseDao mBaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);
    }

    @Override
    protected void initComp() {
        initBorder();
        ButterKnife.bind(this);
        //mContentTrv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mContentTrv.setLayoutManager(new V7GridLayoutManager(this,4));
        mContentTrv.setSpacingWithMargins(20, 20);
        mAdapter = new StayAdapter(this);
    }

    @Override
    protected void initListener() {
        mContentTrv.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                super.onItemSelected(parent, itemView, position);
                mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0));
            }

        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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
        mBaseDao = new DataBaseDao(this);
        items = mBaseDao.getItemInfos(Constans.TYPE_STAY,10);
        items.addAll(mBaseDao.getItemInfos(30));
        Collections.shuffle(items);
        mAdapter.setDataList(items);
        mContentTrv.setAdapter(mAdapter);

        mFilterTfv.setFilterList(DataEngine.getFilterData());
        mFilterTfv.setOnItemClickListener(new com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, FilterBean parentFilter, FilterBean childFilter) {
                //toast("点击了 " + parentFilter.getName() + ":" + childFilter.getName());
                items = mBaseDao.getItemInfos(Constans.TYPE_STAY,10);
                Collections.shuffle(items);
                mAdapter.setDataList(items);

            }
        });

        //获取筛选参数
       /* List<FilterBean> mFilter =  mFilterTfv.getFilterParam();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        NApplication.getRefWatcher(this).watch(this);

    }
}
