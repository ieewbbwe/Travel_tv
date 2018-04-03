package com.wisesoft.traveltv.ui.newdesign;

import com.wisesoft.traveltv.net.request.ItemRequestModel;

import rx.Subscriber;

/**
 * Created by picher on 2018/4/3.
 * Describe：
 */

public interface ListPageInterface {

    void requestData(ItemRequestModel requestModel,BaseFragment.NetWorkListener dataSubscriber);
}
