package com.wisesoft.traveltv.ui.change;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tv.boost.widget.tablayout.TabLayout;
import com.tv.boost.widget.tablayout.TvTabLayout;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.NFragement;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.change.fragment.EatFragment;
import com.wisesoft.traveltv.ui.change.fragment.FunFragment;
import com.wisesoft.traveltv.ui.change.fragment.HomeFragment;
import com.wisesoft.traveltv.ui.change.fragment.PayFragment;
import com.wisesoft.traveltv.ui.change.fragment.PlayFragment;
import com.wisesoft.traveltv.ui.change.fragment.StayFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeChangeActivity extends NActivity {

    @Bind(R.id.m_broadcast_logo_iv)
    ImageView mBroadcastLogoIv;
    @Bind(R.id.m_head_rl)
    RelativeLayout mHeadRl;
    @Bind(R.id.m_main_tab)
    TvTabLayout mMainTab;
    @Bind(R.id.m_main_container_fl)
    FrameLayout mMainContainerFl;
    private FragmentManager mFragmentManager;

    private String mCurrentFragmentTag;
    Fragment fragment = null;
    private long _firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_change);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        //初始化筛选栏
        for (HomeTab tab : HomeTab.values()) {
            mMainTab.addTab(mMainTab.newTab().setText(tab.getNameRes()).setTag(tab));
        }
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initListener() {
        mMainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(mFocusBorder != null){
                    mFocusBorder.setVisible(false);
                }
                HomeTab homeTab = (HomeTab) tab.getTag();
                if (homeTab != null) {
                    switchFragment(homeTab.getNameRes());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int nameRes) {
        if (mFragmentManager != null) {
            String tag = "";
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (mCurrentFragmentTag != null) {
                Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
            }

            switch (nameRes) {
                case R.string.label_home:
                    tag = HomeFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new HomeFragment();
                    }
                    break;
                case R.string.label_home_play:
                    tag = PlayFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new PlayFragment();
                    }
                    break;
                case R.string.label_home_eat:
                    tag = EatFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new EatFragment();
                    }
                    break;
                case R.string.label_home_stay:
                    tag = StayFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new StayFragment();
                    }
                    break;
                case R.string.label_home_pay:
                    tag = PayFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new PayFragment();
                    }
                    break;
                case R.string.label_home_fun:
                    tag = FunFragment.class.getSimpleName();
                    fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment == null) {
                        fragment = new FunFragment();
                    }
                    break;
            }
            if(fragment != null){
                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    transaction.add(R.id.m_main_container_fl, fragment, tag);
                }
                transaction.commit();
                mCurrentFragmentTag = tag;
            }

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                if(fragment != null && fragment instanceof NFragement){
                    return ((NFragement)fragment).onKeyDown(event.getKeyCode(),event);
                }
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                if(fragment != null && fragment instanceof NFragement){
                   return ((NFragement)fragment).onKeyDown(keyCode,event);
                }
                break;
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
                return false;
            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {
        switchFragment(R.string.label_home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
