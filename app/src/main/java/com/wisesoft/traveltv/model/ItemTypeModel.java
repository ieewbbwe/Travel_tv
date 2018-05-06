package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.change.HomeTab;

/**
 * Created by picher on 2018/4/22.
 * Describe：帶類型的item信息封裝
 */

public class ItemTypeModel {

    private ItemInfoBean filterData;
    private HomeTab type;//页面类型

    public ItemTypeModel() {
    }

    public ItemTypeModel(ItemInfoBean filterData, HomeTab type) {
        this.filterData = filterData;
        this.type = type;
    }

    public ItemInfoBean getFilterData() {
        return filterData;
    }

    public void setFilterData(ItemInfoBean filterData) {
        this.filterData = filterData;
    }

    public HomeTab getType() {
        return type;
    }

    public void setType(HomeTab type) {
        this.type = type;
    }
}
