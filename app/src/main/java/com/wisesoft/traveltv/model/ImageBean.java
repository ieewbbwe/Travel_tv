package com.wisesoft.traveltv.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by picher on 2017/9/20.
 * Describe：
 */
@DatabaseTable(tableName = "tb_image")
public class ImageBean extends BaseBean {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String image_path;
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private String definition;
    @DatabaseField(columnName = "img_order")
    private String order;
    //TODO 临时字段，方便演示，按逻辑应该只存在id并与酒店表关联
    @DatabaseField
    private String hotel_id; //所属酒店id

    public ImageBean() {
    }

    public ImageBean(String order,String image_path, String type) {
        this.image_path = image_path;
        this.type = type;
        this.order = order;
    }

    public ImageBean(String order, String image_path, String type, String hotelId) {
        this.order = order;
        this.image_path = image_path;
        this.type = type;
        this.hotel_id = hotelId;
    }

    public String getHotelId() {
        return hotel_id;
    }

    public void setHotelId(String hotelId) {
        this.hotel_id = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public ImageBean(String imgUrl) {
        this.image_path = imgUrl;
    }

    public String getImgUrl() {
        return image_path;
    }

    public void setImgUrl(String imgUrl) {
        this.image_path = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
