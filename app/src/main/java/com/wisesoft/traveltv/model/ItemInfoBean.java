package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.internal.IItemInfo;

import java.util.List;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */

public class ItemInfoBean extends BaseBean implements IItemInfo {
    private String imagePath;
    private String name;
    private String videoId;
    private List<ImageBean> imageList;
    private String grade;
    private String address;
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ItemInfoBean() {
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<ImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageBean> imageList) {
        this.imageList = imageList;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public String getGradeStr(){
        return "综合评分："+grade;
    }
    public String getAddressStr(){
        return "地址："+address;
    }
    public String getPhoneStr(){
        return "预约电话："+phone;
    }
}
