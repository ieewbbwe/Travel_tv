package com.wisesoft.traveltv.ui.newdesign.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.newdesign.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by picher on 2018/4/12.
 * Describe：新设计的列表基类
 */

public abstract class BaseListFragment extends BaseFragment {

    @Bind(R.id.m_header_container)
    FrameLayout mHeaderContainer;
    @Bind(R.id.m_filter_trv)
    TvRecyclerView mFilterTrv;
    @Bind(R.id.m_list_trv)
    TvRecyclerView mListTrv;
    private View mHeaderView;

    //獲取頭部佈局
    public abstract int getHeaderLayout();

    @Override
    protected int create() {
        return R.layout.fragment_new_design_list;
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this, v);
        //初始化头部布局
        mHeaderView = LayoutInflater.from(getActivity()).inflate(getHeaderLayout(),null);
        mHeaderContainer.addView(mHeaderView);
        //初始化筛选布局

        //初始化列表布局
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean hasFocus() {
        return false;
    }

    @Override
    public void requestFocus() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
