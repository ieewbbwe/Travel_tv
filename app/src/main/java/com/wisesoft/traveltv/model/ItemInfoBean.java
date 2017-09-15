package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.internal.IItemInfo;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */

public class ItemInfoBean extends BaseBean implements IItemInfo {
    private String imagePath;
    private String name;
    private String videoId;

    public ItemInfoBean() {
    }

    public ItemInfoBean(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String getImgUrl() {
        return imagePath;
    }

    @Override
    public String getVideoId() {
        return videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
