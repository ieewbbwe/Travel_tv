package com.wisesoft.traveltv.model.temp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wisesoft.traveltv.internal.IFilter;

import java.util.List;

/**
 * Created by picher on 2017/9/24.
 * Describe：筛选对象
 */

@DatabaseTable(tableName = "tb_init_data")
public class InitDataBean implements IFilter {

    @DatabaseField
    private String name;
    //父级别需要设置子级别条件
    private List<InitDataBean> childBean;

    @DatabaseField
    private String id;
    @DatabaseField
    private String id_core;
    @DatabaseField
    private String update_time;
    @DatabaseField
    private String other;
    @DatabaseField
    private String parent_id;

    public InitDataBean(){}

    public InitDataBean(String code, String name) {
        this.id = code;
        this.name = name;
    }

    public List<InitDataBean> getChildBean() {
        return childBean;
    }

    public void setChildBean(List<InitDataBean> childBean) {
        this.childBean = childBean;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String getCode() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.id = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_core() {
        return id_core;
    }

    public void setId_core(String id_core) {
        this.id_core = id_core;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
