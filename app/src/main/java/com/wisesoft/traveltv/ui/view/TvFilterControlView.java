package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.wisesoft.traveltv.model.temp.InitDataBean;

import java.util.List;

/**
 * Created by picher on 2018/4/12.
 * Describe：
 */

public class TvFilterControlView extends RelativeLayout {
    public List<InitDataBean> initDataBeans;

    public TvFilterControlView(Context context) {
        this(context,null);
    }

    public TvFilterControlView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TvFilterControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void setFilterData(List<InitDataBean> filterData){
        this.initDataBeans = filterData;

    }
}
