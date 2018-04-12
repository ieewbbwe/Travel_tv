package com.wisesoft.traveltv.ui.newdesign;

import android.os.Bundle;

import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.change.HomeTab;
import com.wisesoft.traveltv.ui.newdesign.page.PlayFragment;
import com.wisesoft.traveltv.ui.newdesign.page.SearchFragment;

/**
 * Created by picher on 2018/4/1.
 * Describe：
 */

public class FragmentFactory {

    public static BaseFragment create(HomeTab homeTab){
        BaseFragment baseFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseFragment.ARG_HOME_TAB,homeTab);
        baseFragment.setArguments(bundle);
        switch (homeTab.getNameRes()){
            case R.string.label_home_search:
                baseFragment = new SearchFragment();
                break;
            case R.string.label_recommend:
                break;
            case R.string.label_home_play:
                baseFragment = new PlayFragment();
                break;
            case R.string.label_home_eat:
                break;
            case R.string.label_home_stay:
                break;
            case R.string.label_home_pay:
                break;
            case R.string.label_home_fun:
                break;
        }

        return baseFragment;
    }
}
