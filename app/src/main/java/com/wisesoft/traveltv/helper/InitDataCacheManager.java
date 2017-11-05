package com.wisesoft.traveltv.helper;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android_mobile.core.manager.SharedPrefManager;
import com.android_mobile.net.response.BaseResponse;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;

import java.util.List;

import retrofit2.Response;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by picher on 2017/11/5.
 * Describe：
 */

public class InitDataCacheManager {
    private DataBaseDao mDao;

    public InitDataCacheManager(@NonNull DataBaseDao baseDao){
        this.mDao = baseDao;
    }

    public void start(){
        if(mDao == null){
            throw  new IllegalArgumentException("dao is not be null");
        }
        String lastUpdateTime = SharedPrefManager.getString(Constans.CACHE_INIT_UPDATE_TIME,"");
        ApiFactory.getTravelApi().initData(lastUpdateTime)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<InitDataBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<InitDataBean>>> response) {
                        if(response.code() == 200){
                            mDao.initDatabase(response.body().getResponse());
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {

                    }
                });
    }

}
