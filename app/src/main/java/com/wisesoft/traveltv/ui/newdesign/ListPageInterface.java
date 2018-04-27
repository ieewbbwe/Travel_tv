package com.wisesoft.traveltv.ui.newdesign;

import com.wisesoft.traveltv.net.request.ItemRequestModel;

/**
 * Created by picher on 2018/4/3.
 * Describe：
 */

public interface ListPageInterface {

    void requestData(ItemRequestModel requestModel,BaseNewDesignFragment.NetWorkListener dataSubscriber);
}
