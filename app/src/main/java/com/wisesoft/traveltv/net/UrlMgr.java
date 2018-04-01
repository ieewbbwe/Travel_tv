package com.wisesoft.traveltv.net;

/**
 * Created by mxh on 2017/8/8.
 * Describe：接口地址
 */

public interface UrlMgr {

    String HOST = "http://192.168.0.101";
    //String HOST = "http://api.965927.com";
    String PORT = ":8000";
    String SITE_NAME = "/wzyc";
    //String API = "/wzyc/api/v1/";
    String API = "/api/v1/";
    String Service = HOST + PORT + API;

    /*获取新闻tab*/
    String URL_CATEGORY_TAB = "/enews4tv/api/categorys";

    /*数据初始化*/
    String URL_INIT_DATA = "initData";
    /*获取数据列表*/
    String URL_GET_LIST = "projectList";
    /*获取数据详情*/
    String URL_GET_DETAIL = "projectDetail";
    /*搜索*/
    String URL_SEARCH = "search";
    /*推荐*/
    String URL_GET_RECOMMEND = "recommend";
    /*产品二级列表*/
    String URL_GET_PROJECT_RECOMMEND = "projectRecommend";
    /*首页广告*/
    String URL_GET_BANNER = "banner";
}
