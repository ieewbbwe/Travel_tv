package com.wisesoft.traveltv.model;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */

public class ItemInfoBean extends BaseBean {
    private String imagePath;
    private String name;
    public ItemInfoBean() {
    }

    public ItemInfoBean(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
