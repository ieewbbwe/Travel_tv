package com.wisesoft.traveltv.model;

/**
 * Created by picher on 2017/9/20.
 * Describe：
 */

public class ImageBean extends BaseBean {

    private String imgUrl;
    private String type;//清晰度

    public ImageBean() {
    }

    public ImageBean(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
