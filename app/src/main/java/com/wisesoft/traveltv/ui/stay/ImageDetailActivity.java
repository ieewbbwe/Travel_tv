package com.wisesoft.traveltv.ui.stay;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.ui.view.TVIconView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends NActivity {

    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
    @Bind(R.id.m_grade_tv)
    TextView mGradeTv;
    @Bind(R.id.m_phone_tv)
    TextView mPhoneTv;
    @Bind(R.id.m_address_tv)
    TextView mAddressTv;
    @Bind(R.id.m_image_display_fl)
    FrameLayout mImageDisplayFl;            //图片展示位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
