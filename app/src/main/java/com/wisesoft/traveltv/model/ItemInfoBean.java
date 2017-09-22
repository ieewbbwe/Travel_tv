package com.wisesoft.traveltv.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.IItemInfo;

import java.util.List;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */
@DatabaseTable(tableName = "tb_item_info")
public class ItemInfoBean extends BaseBean implements IItemInfo {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String image_url;
    @DatabaseField
    private String name;
    @DatabaseField
    private String video_id;
    @DatabaseField
    private String image_id;
    @DatabaseField
    private double recommend;
    @DatabaseField
    private long create_time;
    @DatabaseField
    private String introduce;
    @DatabaseField
    private String type;
    @DatabaseField
    private long view_count;

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


    public ItemInfoBean(String image_url, String name, double recommend, long create_time, String introduce, String type, long view_count, String address, String phone) {
        this.image_url = image_url;
        this.name = name;
        this.recommend = recommend;
        this.create_time = create_time;
        this.introduce = introduce;
        this.type = type;
        this.view_count = view_count;
        this.address = address;
        this.phone = phone;
    }

    public ItemInfoBean() {
    }

    public double getGrade() {
        return recommend;
    }

    public void setGrade(double grade) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getView_count() {
        return view_count;
    }

    public void setView_count(long view_count) {
        this.view_count = view_count;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduceStr() {
        return "景点介绍：\n " + introduce;
    }

    public String getAnotherStr() {
        String otherInfo = null;
        if (type.equals(Constans.TYPE_PLAY)) {
            otherInfo = "门票价格：160元/人     开放时间：08:00 - 17:00";
        } else {
            otherInfo = "人均消费：88元/人     营业时间：08:00 - 17:00";
        }

        return otherInfo + "\n" + getAddressStr();
    }

}
