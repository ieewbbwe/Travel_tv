package com.wisesoft.traveltv.ui.newdesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.net.response.BaseResponse;
import com.wisesoft.traveltv.NFragement;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.internal.OnItemInfoLoadListener;
import com.wisesoft.traveltv.manager.ProductManager;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.net.request.ItemRequestModel;
import com.wisesoft.traveltv.ui.change.HomeTab;

import java.util.List;

import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by picher on 2018/4/1.
 * Describe：新UI的页面基类
 */

public abstract class BaseNewDesignFragment extends NFragement implements ListPageInterface {

    public static final String ARG_HOME_TAB = "arg_home_tab";

    private HomeTab tab;
    private String mPageType;
    private int page = 1;
    private int limit = 9;
    private boolean isLoading;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tab = (HomeTab) getArguments().get(ARG_HOME_TAB);
        mPageType = ProductManager.Companion.getInstance().getPageType(tab);
    }

    public String getPageTitle() {
        if (tab != null) {
            return getResources().getString(tab.getNameRes());
        }
        return "";
    }

    @Override
    public void requestData(ItemRequestModel itemRequestModel, final NetWorkListener listener) {
        ApiFactory.getTravelApi().getProductList(mPageType, itemRequestModel.getArea(), itemRequestModel.getStar(), itemRequestModel.getSight(),
                itemRequestModel.getFood_type(), itemRequestModel.getP_h(), itemRequestModel.getP_low(), limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        isLoading = false;
                        if (CollectionUtils.isNotEmpty(response.body().getResponse())) {
                            listener.onResponse(response.body());
                        } else {
                            toast("没有更多的数据了");
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        if (isLoading) {
                            isLoading = false;
                            page--;
                        }
                        toast(message);
                    }
                });
    }

    public void jumpToDetail(ItemInfoBean itemInfoBean) {
        Intent intent = new Intent(activity, ProjectDetailNewDesignActivity.class);
        intent.putExtra(Constans.ITEM_BEAN, itemInfoBean);
        pushActivity(intent, false);
    }

    public boolean hasFocus() {
        return v != null && ((ViewGroup) v).getFocusedChild() != null;
    }

    public abstract void requestFocus();

    public interface NetWorkListener {
        void onResponse(BaseResponse baseResponse);
    }

    /**
     * 获取推荐数据
     */
    public void getRecommendData(String type, int size, int page, final OnItemInfoLoadListener listener) {
        ApiFactory.getTravelApi().getRecommend(type, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        if (listener != null) {
                            listener.onLoadSucceed(response.body().getResponse());
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        toast(message);
                    }
                });
    }
}
