package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by picher on 2018/4/28.
 * Describe：
 */

public class SpannableItemModel {

    private ItemInfoBean itemInfoBean;

    private int rowSpan;
    private int colSpan;

    public SpannableItemModel(ItemInfoBean itemInfoBean) {
        this.itemInfoBean = itemInfoBean;
    }

    public SpannableItemModel() {
    }

    public int getColSpan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public ItemInfoBean getItemInfoBean() {
        return itemInfoBean;
    }

    public void setItemInfoBean(ItemInfoBean itemInfoBean) {
        this.itemInfoBean = itemInfoBean;
    }
}
