package com.wisesoft.traveltv.net.api;

import com.android_mobile.net.response.BaseResponse;
import com.wisesoft.traveltv.model.temp.BannerBean;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.UrlMgr;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mxh on 2017/8/8.
 * Describe：新闻类接口
 */

public interface TravelApi {

    @GET(UrlMgr.URL_CATEGORY_TAB)
    Observable<Response> getCategoryInfo();

    @GET(UrlMgr.URL_INIT_DATA)
    Observable<Response<BaseResponse<List<InitDataBean>>>> initData(@Query("update_time") String updateTime);
    @GET(UrlMgr.URL_GET_LIST)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>>
    getProductList(@Query("type") String type,@Query("area") String area,@Query("star") String star,
                   @Query("sight") String sight,@Query("food_type") String food,@Query("price_low") double p_low, @Query("price_heigh") double p_h,
                   @Query("limit") int limit, @Query("page") int page);

    @GET(UrlMgr.URL_GET_LIST)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>>
    getProductList(@Query("type") String type,@Query("area") String area,@Query("star") String star,@Query("pay_type") String pay_type,
                   @Query("fun_type") String fun_type, @Query("sight") String sight,@Query("food_type") String food,@Query("price_low") double p_low,
                   @Query("price_heigh") double p_h, @Query("limit") int limit, @Query("page") int page);

    @GET(UrlMgr.URL_GET_RECOMMEND)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>> getRecommend(@Query("type") String type, @Query("limit") int limit, @Query("page") int page);

    @GET(UrlMgr.URL_GET_DETAIL)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>> getDetail(@Query("id") String id);//暂时写成List 以后改
    @GET(UrlMgr.URL_SEARCH)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>> getSearchList(@Query("key") String key,@Query("limit") int limit, @Query("page") int page);

    @GET(UrlMgr.URL_GET_PROJECT_RECOMMEND)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>> getProduceRecommend(@Query("id") String id);//暂时写成List 以后改

    @GET(UrlMgr.URL_GET_BANNER)
    Observable<Response<BaseResponse<List<ItemInfoBean>>>> getBanner();
}
