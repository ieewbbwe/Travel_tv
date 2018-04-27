package com.wisesoft.traveltv.ui.newdesign;

import android.os.Bundle;

import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.change.HomeTab;
import com.wisesoft.traveltv.ui.newdesign.page.HotFragment;
import com.wisesoft.traveltv.ui.newdesign.page.ListHeaderFragment;
import com.wisesoft.traveltv.ui.newdesign.page.SearchFragment;

/**
 * Created by picher on 2018/4/1.
 * Describe：
 */

public class FragmentFactory {

    public static BaseNewDesignFragment create(HomeTab homeTab){
        BaseNewDesignFragment baseFragment = new SearchFragment();
        switch (homeTab.getNameRes()){
            case R.string.label_home_search:
                baseFragment = new SearchFragment();
                break;
            case R.string.label_recommend:
                baseFragment = new HotFragment();
                break;
            case R.string.label_home_play:
                //baseFragment = new PlayFragment();
            case R.string.label_home_eat:
            case R.string.label_home_stay:
            case R.string.label_home_pay:
            case R.string.label_home_fun:
                baseFragment = new ListHeaderFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseNewDesignFragment.ARG_HOME_TAB, homeTab);
        baseFragment.setArguments(bundle);

        return baseFragment;
    }
}
