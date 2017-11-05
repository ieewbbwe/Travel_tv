package com.wisesoft.traveltv.net;

import com.android_mobile.net.BaseFactory;
import com.wisesoft.traveltv.net.api.TravelApi;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by picher on 2017/11/5.
 * Describe：
 */

public class ApiFactory extends BaseFactory {

    private static TravelApi travelApi;

    public static TravelApi getTravelApi() {
        if (travelApi == null) {
            travelApi = createApi(UrlMgr.Service, GsonConverterFactory.create(), TravelApi.class);
        }
        return travelApi;
    }
}
