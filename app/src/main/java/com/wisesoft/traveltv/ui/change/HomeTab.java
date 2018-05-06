package com.wisesoft.traveltv.ui.change;

import com.wisesoft.traveltv.R;

import static android.R.attr.name;

/**
 * Created by picher on 2018/1/6.
 * Describe：
 */

public enum HomeTab {
    TAB_SEARCH(R.string.label_home_search),
    TAB_RECOMMEND(R.string.label_recommend),
    TAB_PLAY(R.string.label_home_play),
    TAB_EAT(R.string.label_home_eat),
    TAB_STAY(R.string.label_home_stay),
    TAB_PAY(R.string.label_home_pay),
    TAB_FUN(R.string.label_home_fun);

    private int nameRes;
    HomeTab(int name) {
        this.nameRes = name;
    }

    public int getNameRes() {
        return nameRes;
    }
}
