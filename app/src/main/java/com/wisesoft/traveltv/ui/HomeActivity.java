package com.wisesoft.traveltv.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.GalleryAdapter;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.play.AmusementActivity;
import com.wisesoft.traveltv.ui.stay.StayActivity;
import com.wisesoft.traveltv.ui.view.TVIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by mxh on 2017.09.05
 * Describe：应用首页
 */
public class HomeActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_bottom_bar)
    ViewGroup mBottomBar;
    @Bind(R.id.m_search_rb)
    TVIconView mSearchRb;
    @Bind(R.id.m_play_rb)
    TVIconView mPlayRb;
    @Bind(R.id.m_eat_rb)
    TVIconView mEatRb;
    @Bind(R.id.m_stay_rb)
    TVIconView mStayRb;
    @Bind(R.id.m_traffic_rb)
    TVIconView mTrafficRb;
    @Bind(R.id.m_settings_rb)
    TVIconView mSettingsRb;
    @Bind(R.id.m_gallery_cf)
    FeatureCoverFlow mGalleryCf;

    private List<ItemInfoBean> beanList = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.push_fadein_in, R.anim.push_fadein_out);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        mSearchRb.setOnClickListener(this);
        mPlayRb.setOnClickListener(this);
        mEatRb.setOnClickListener(this);
        mStayRb.setOnClickListener(this);
        mTrafficRb.setOnClickListener(this);
        mSettingsRb.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        /*-----测试数据-------*/
        beanList.add(new ItemInfoBean(""));
        beanList.add(new ItemInfoBean(""));
        beanList.add(new ItemInfoBean(""));
        beanList.add(new ItemInfoBean(""));
        /*-----测试数据完------*/
        mGalleryAdapter = new GalleryAdapter(beanList, this);
        mGalleryCf.setAdapter(mGalleryAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isFocused(mGalleryCf)) {
                    if (mSearchRb != null)
                        mSearchRb.requestFocus();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isFocused(mSearchRb) || isFocused(mPlayRb) || isFocused(mEatRb)
                        || isFocused(mStayRb) || isFocused(mTrafficRb) || isFocused(mSettingsRb)) {
                    if (mGalleryCf != null)
                        mGalleryCf.requestFocus();
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_play_rb:
                pushActivity(AmusementActivity.class);
                break;
            case R.id.m_stay_rb:
                pushActivity(StayActivity.class);
                break;
            case R.id.m_search_rb:
            case R.id.m_eat_rb:
            case R.id.m_traffic_rb:
            case R.id.m_settings_rb:
                toast("Wait For Codding...");
                break;
        }
    }
}
