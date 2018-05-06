package com.wisesoft.traveltv.internal;

import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.List;

/**
 * Created by picher on 2018/5/3.
 * Describe：
 */

public interface OnItemInfoLoadListener {

    void onLoadSucceed(List<ItemInfoBean> itemInfoBeans);
}
