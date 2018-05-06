package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.change.HomeTab;

import java.util.List;

/**
 * Created by picher on 2018/4/28.
 * Describe：
 */

public class HotListItemModel {

    private List<ItemInfoBean> itemInfoBeans;
    private String type;//類型
    private int rowSize;//行數
    private int columnSize;//列數
    private int itemSize;//縂條目數
    private int height;//佈局高

    public HotListItemModel(List<ItemInfoBean> itemInfoBeans, String type, int rowSize, int columnSize, int itemSize, int height) {
        this.itemInfoBeans = itemInfoBeans;
        this.type = type;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.itemSize = itemSize;
        this.height = height;
    }

    public List<ItemInfoBean> getItemInfoBeans() {
        return itemInfoBeans;
    }

    public void setItemInfoBeans(List<ItemInfoBean> itemInfoBeans) {
        this.itemInfoBeans = itemInfoBeans;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HotListItemModel() {
    }

    public HotListItemModel(List<ItemInfoBean> initDataBean, String type) {
        this.itemInfoBeans = initDataBean;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
