package com.wisesoft.traveltv.ui.view.weight.pop;

import android.view.View;

import com.wisesoft.traveltv.model.temp.InitDataBean;

/**
 * Created by picher on 2017/9/24.
 * Describe：
 */

public interface OnItemClickListener {

    public void OnItemClick(View v, InitDataBean parentFilter, InitDataBean childFilter);
}
