package com.wisesoft.traveltv.model.temp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wisesoft.traveltv.model.BaseBean;

/**
 * Created by picher on 2017/9/9.
 * Describe：视屏对象
 */
@DatabaseTable(tableName = "tb_video")
public class VideoBean extends BaseBean {

    @DatabaseField
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String video_url;
    @DatabaseField
    private String type;
    @DatabaseField
    private String other;
    @DatabaseField
    private String definition;

    public VideoBean() {
    }

    public VideoBean(String name, String video_url, String type) {
        this.name = name;
        this.video_url = video_url;
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
