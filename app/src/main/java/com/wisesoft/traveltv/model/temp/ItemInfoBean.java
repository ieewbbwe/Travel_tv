package com.wisesoft.traveltv.model.temp;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.IItemInfo;
import com.wisesoft.traveltv.model.BaseBean;
import com.wisesoft.traveltv.net.UrlMgr;

import java.io.Serializable;
import java.util.List;

/**
 * Created by picher on 2017/9/9.
 * Describe：条目信息对象
 */
@DatabaseTable(tableName = "tb_item_info")
public class ItemInfoBean extends BaseBean implements IItemInfo,Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String img_f;
    @DatabaseField
    private String file_f;
    @DatabaseField
    private String video_path;
    @DatabaseField
    private String title;
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
    private String type_str;
    @DatabaseField
    private long view_count;
    //TODO 临时字段
    @DatabaseField
    private String hotel_id;  //所在type的位置

    @DatabaseField
    private String address;
    @DatabaseField
    private String tel_num;
    @DatabaseField
    private double minPrice;
    @DatabaseField
    private double maxPrice;

    private int like_count;
    private String open_time_end;
    private String open_time_start;
    private List<Integer> child;
    private double price;

    private List<ImageBean> images;
    private List<VideoBean> videos;
    private Integer imgRes;
    private Integer recommend_index;

    public String getHotel_id() {
        return hotel_id;
    }

    public String getOpen_time_end() {
        return open_time_end;
    }

    public List<Integer> getChild() {
        return child;
    }

    public Integer getImgRes() {
        return imgRes;
    }

    public void setImgRes(Integer imgRes) {
        this.imgRes = imgRes;
    }

    public void setChild(List<Integer> child) {
        this.child = child;
    }

    public void setOpen_time_end(String open_time_end) {
        this.open_time_end = open_time_end;
    }

    public String getOpen_time_start() {
        return open_time_start;
    }

    public void setOpen_time_start(String open_time_start) {
        this.open_time_start = open_time_start;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }


    public String getFile_f() {
        if (!TextUtils.isEmpty(file_f)) {
            return (UrlMgr.HOST + UrlMgr.PORT + file_f).trim();
        }
        return "";
    }

    public void setFile_f(String file_f) {
        this.file_f = file_f;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel_num() {
        return tel_num;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }


    public ItemInfoBean(String image_url, String type) {
        this.img_f = image_url;
        this.type_str = type;
    }

    public ItemInfoBean(int imgRes, String type) {
        this.imgRes = imgRes;
        this.type_str = type;
    }

    public ItemInfoBean(String image_url, String name, double recommend, long create_time, String introduce, String type, long view_count, String address, String phone) {
        this.img_f = image_url;
        this.title = name;
        this.recommend = recommend;
        this.create_time = create_time;
        this.introduce = introduce;
        this.type_str = type;
        this.view_count = view_count;
        this.address = address;
        this.tel_num = phone;
    }

    public ItemInfoBean(int image_url, String name, double recommend, long create_time, String introduce, String type, long view_count, String address, String phone) {
        this.imgRes = image_url;
        this.title = name;
        this.recommend = recommend;
        this.create_time = create_time;
        this.introduce = introduce;
        this.type_str = type;
        this.view_count = view_count;
        this.address = address;
        this.tel_num = phone;
    }

    public ItemInfoBean(String image_url, String name, double recommend,
                        long create_time, String introduce, String type,
                        long view_count, String address, String phone, String hotel_id) {
        this.img_f = image_url;
        this.title = name;
        this.recommend = recommend;
        this.create_time = create_time;
        this.introduce = introduce;
        this.type_str = type;
        this.view_count = view_count;
        this.address = address;
        this.tel_num = phone;
        this.hotel_id = hotel_id;
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
        this.img_f = imagePath;
    }

    public ItemInfoBean(String title, long view_count) {
        this.title = title;
        this.view_count = view_count;
    }

    public ItemInfoBean(String img_f, int id) {
        this.img_f = img_f;
        this.id = id;
    }

    public void setImagePath(String imagePath) {
        this.img_f = imagePath;
    }

    @Override
    public String getImgUrl() {
        if (!TextUtils.isEmpty(img_f)) {
            if (img_f.startsWith("/upload") || img_f.startsWith("/wzyc") || img_f.startsWith("news")) {
                return (UrlMgr.HOST + UrlMgr.PORT + img_f).trim();
            }
            return img_f.trim();
        }
        return "";
    }

    @Override
    public String getVideoId() {
        return video_id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getType() {
        return type_str;
    }

    public void setType(String type) {
        this.type_str = type;
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
        return "电话：" + tel_num;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduceStr() {
        String str;
        switch (type_str) {
            case Constans.TYPE_EAT:
                str = "美食介绍：";
                break;
            case Constans.TYPE_STAY:
                str = "酒店介绍：";
                break;
            case Constans.TYPE_PLAY:
                str = "景点介绍：";
                break;
            default:
                str = "商品介绍：";
                break;
        }
        return str + "\n " + introduce;

    }

    @Deprecated
    public String getAnotherStr() {
        String otherInfo = null;
        if (type_str.equals(Constans.TYPE_PLAY)) {
            otherInfo = "门票价格：160元/人     开放时间：08:00 - 17:00";
        } else {
            otherInfo = "人均消费：88元/人     营业时间：08:00 - 17:00";
        }

        return otherInfo + "\n" + getAddressStr();
    }

    public String getPriceTimeStr() {
        String otherInfo = null;
        if (type_str.equals(Constans.TYPE_PLAY)) {
            otherInfo = "门票价格：" + price + "元/人  开放时间：" + open_time_start + " - " + open_time_end;
        } else {
            otherInfo = "人均消费：" + price + "元/人  营业时间：" + open_time_start + " - " + open_time_end;
        }
        return otherInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
