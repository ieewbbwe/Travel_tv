package com.wisesoft.traveltv.constants;

/**
 * Created by mxh on 2017/8/10.
 * Describe：常量类
 */

public interface Constans {

    /*————缓存信息标签————*/
    /*session缓存*/
    String CACHE_SESSION = "cache_session";
    String CACHE_INIT_UPDATE_TIME = "cache_init_update_time";

    /*————新闻类型——————*/
    /*视频类新闻*/
    int NEWS_TYPE_VIDEO = 0x1;
    /*专题类新闻*/
    int NEWS_TYPE_TOPIC = 0x2;

    /*————数据标签————*/
    /*新闻跳转标签*/
    String ITEM_BEAN = "item_bean";

    String TYPE_PLAY = "play";
    String TYPE_SCENERY = "scenery";
    String TYPE_EAT = "eat";
    String TYPE_FOOD = "food";
    String TYPE_STAY = "stay";
    String TYPE_HOME = "home";

    String TYPE_TRAFFIC = "traffic";
    String IS_INIT_DATA = "is_init_data";

    //为演示临时添加
    String TYPE_HOTEL_FOOD = "hotel";

    //传递的参数
    String ARG_PAGE_TYPE = "arg_page_type";
}
