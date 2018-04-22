package com.wisesoft.traveltv.model;

import android.support.annotation.IntDef;

import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.ui.change.HomeTab;

/**
 * Created by picher on 2018/4/19.
 * Describe：
 */

public class FilterItemModel {
    private InitDataBean filterData;
    private HomeTab type;//页面类型

    public FilterItemModel() {
    }

    public FilterItemModel(InitDataBean filterData, HomeTab type) {
        this.filterData = filterData;
        this.type = type;
    }

    public InitDataBean getFilterData() {
        return filterData;
    }

    public void setFilterData(InitDataBean filterData) {
        this.filterData = filterData;
    }

    public HomeTab getType() {
        return type;
    }

    public void setType(HomeTab type) {
        this.type = type;
    }
}
