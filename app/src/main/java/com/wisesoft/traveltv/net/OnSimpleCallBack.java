package com.wisesoft.traveltv.net;

import android.util.Log;

import com.android_mobile.core.utiles.Utiles;
import com.android_mobile.net.NetUtils;
import com.android_mobile.net.OnResultCallBack;
import com.android_mobile.net.response.BaseResponse;
import com.google.gson.Gson;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by picher on 2017/11/5.
 * Describe：
 */

public abstract class OnSimpleCallBack<T extends Response> extends Subscriber<T> {
    public abstract void onResponse(T response);
    public abstract void onFailed(int code, String message);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFailed(500,"请求失败");
    }

    @Override
    public void onNext(T response) {
        if (response.isSuccessful() && isOk(response)) {
            onResponse(response);
        } else {
            if (response.body() instanceof BaseResponse) {
                onFailed(response.code(), ((BaseResponse) response.body()).getMessage());//response.message());
            } else {
                onFailed(response.code(), "请求失败");//response.message());
            }
        }
    }

    /**
     * 自定义响应数据拦截
     *
     * @param response 响应结果
     * @return true pass false not
     */
    private boolean isOk(T response) {
        if (response.body() instanceof BaseResponse) {
            return true;
        } else {
            return true;
        }
    }


}
