package com.wisesoft.traveltv.ui.newdesign;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.tv.boost.widget.tablayout.TvTabLayout;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.change.HomeTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeNewDesignActivity extends NActivity {

    @Bind(R.id.m_main_tab)
    TvTabLayout mMainTab;
    @Bind(R.id.m_container_vp)
    ViewPager mContainerVp;
    private FragmentManager mFragmentManager;
    private ContainerPagerAdapter mPagerAdapter;

    private List<BaseFragment> mBaseFragmnets = new ArrayList<>();
    private List<HomeTab> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new_design);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        //初始化標簽欄 工厂模式添加fragment
        for (HomeTab tab : HomeTab.values()) {
            mBaseFragmnets.add(FragmentFactory.create(tab));
            mTabs.add(tab);
        }
        //初始化ViewPager
        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new ContainerPagerAdapter(mFragmentManager,this);
        mPagerAdapter.setData(mBaseFragmnets);
        mPagerAdapter.setTitleData(mTabs);
        mContainerVp.setAdapter(mPagerAdapter);
        mMainTab.setupWithViewPager(mContainerVp);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
