package com.wisesoft.traveltv.internal;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by picher on 2018/4/28.
 * Describe：
 */

public interface ITitleAdapter {

    View getTitleView(int index, RecyclerView parent);
}
