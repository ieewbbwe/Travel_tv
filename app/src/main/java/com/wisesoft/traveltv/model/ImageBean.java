package com.wisesoft.traveltv.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by picher on 2017/9/20.
 * Describe：
 */
@DatabaseTable(tableName = "tb_image")
public class ImageBean extends BaseBean {

    @DatabaseField
    private String id;
    @DatabaseField
    private String image_path;
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private String definition;

    public ImageBean() {
    }

    public ImageBean(String id, String image_path, String type) {
        this.id = id;
        this.image_path = image_path;
        this.type = type;
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
