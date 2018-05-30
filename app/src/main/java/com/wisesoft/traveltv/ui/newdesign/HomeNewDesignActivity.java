package com.wisesoft.traveltv.ui.newdesign;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.android_mobile.core.utiles.Lg;
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

    private List<BaseNewDesignFragment> mBaseFragmnets = new ArrayList<>();
    private List<HomeTab> mTabs = new ArrayList<>();
    private BaseNewDesignFragment mCurrentFragment;
    private double _firstTime;

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
        mPagerAdapter = new ContainerPagerAdapter(mFragmentManager, this);
        mPagerAdapter.setData(mBaseFragmnets);
        mPagerAdapter.setTitleData(mTabs);
        mContainerVp.setAdapter(mPagerAdapter);
        mContainerVp.setCurrentItem(1);
        mMainTab.setupWithViewPager(mContainerVp);

        //mContainerVp.setOffscreenPageLimit(8);

    }

    @Override
    protected void initListener() {
        mContainerVp.addOnPageChangeListener(new OnSimplePageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentFragment = mBaseFragmnets.get(position);
                Lg.d("picher", "onPageChange:" + position);
            }
        });

        mMainTab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Lg.d("picher", "Tab获取焦点:" + hasFocus);
            }
        });
    }

    @Override
    protected void initData() {
        //默认位置 热门推荐
        mContainerVp.setCurrentItem(1);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) { //不响应抬起事件 防止回掉两次
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mMainTab != null && mCurrentFragment != null) {
                        if (mMainTab.hasFocus() && !mCurrentFragment.hasFocus()) {
                            mCurrentFragment.requestFocus();
                            return true;
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
            }
        }
        return mBaseFragmnets.get(mContainerVp.getCurrentItem()).dispatchFragmentKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //首页和搜索页 需要拦截Back事件给SearchView
                long secondTime = System.currentTimeMillis();
                if (secondTime - _firstTime > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
                    toast("再按一次退出程序...");
                    _firstTime = secondTime;// 更新firstTime
                    return true;
                } else {
                    exitAppWithToast();
                }
        }
        return mBaseFragmnets.get(mContainerVp.getCurrentItem()).onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mBaseFragmnets.get(mContainerVp.getCurrentItem()).onKeyUp(keyCode, event);
    }

    public void requestFocus() {
        mMainTab.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
