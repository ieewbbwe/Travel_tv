package com.wisesoft.traveltv.model;

/**
 * Created by picher on 2018/4/4.
 * Describe：
 */

public class KeyBoardItemBean extends BaseBean{

    private String name;

    public KeyBoardItemBean(char i) {
        name = String.valueOf(i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
