package com.wisesoft.traveltv.model;

import com.wisesoft.traveltv.internal.IFilter;

import java.util.List;

/**
 * Created by picher on 2017/9/24.
 * Describe：筛选对象
 */

public class FilterBean implements IFilter {

    private String code;
    private String name;
    //父级别需要设置子级别条件
    private List<FilterBean> childBean;

    public FilterBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public List<FilterBean> getChildBean() {
        return childBean;
    }

    public void setChildBean(List<FilterBean> childBean) {
        this.childBean = childBean;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
