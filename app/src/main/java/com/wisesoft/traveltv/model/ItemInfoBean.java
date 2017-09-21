package com.wisesoft.traveltv.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wisesoft.traveltv.internal.IItemInfo;

import java.util.List;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */
@DatabaseTable(tableName = "tb_item_info")
public class ItemInfoBean extends BaseBean implements IItemInfo {

    @DatabaseField
    private String id;
    @DatabaseField
    private String image_url;
    @DatabaseField
    private String name;
    @DatabaseField
    private String video_id;
    @DatabaseField
    private String image_id;
    @DatabaseField
    private String recommend;
    @DatabaseField
    private String create_time;
    @DatabaseField
    private String introduce;
    @DatabaseField
    private String type;
    @DatabaseField
    private String view_count;

    @DatabaseField
    private String address;
    @DatabaseField
    private String phone;

    private List<ImageBean> images;
    private List<VideoBean> videos;


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
        return recommend;
    }

    public void setGrade(String grade) {
        this.recommend = grade;
    }

    public List<ImageBean> getImageList() {
        return images;
    }

    public void setImageList(List<ImageBean> imageList) {
        this.images = imageList;
    }

    public void setVideoId(String videoId) {
        this.video_id = videoId;
    }

    public ItemInfoBean(String imagePath) {
        this.image_url = imagePath;
    }

    public String getImagePath() {
        return image_url;
    }

    public void setImagePath(String imagePath) {
        this.image_url = imagePath;
    }

    @Override
    public String getImgUrl() {
        return image_url;
    }

    @Override
    public String getVideoId() {
        return video_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeStr() {
        return "综合评分：" + recommend;
    }

    public String getAddressStr() {
        return "地址：" + address;
    }

    public String getPhoneStr() {
        return "预约电话：" + phone;
    }
}
