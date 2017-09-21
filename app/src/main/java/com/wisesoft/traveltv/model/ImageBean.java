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

    public ImageBean() {
    }

    public ImageBean(String order,String image_path, String type) {
        this.image_path = image_path;
        this.type = type;
        this.order = order;
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
