package com.wisesoft.traveltv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android_mobile.core.utiles.Lg;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.GalleryAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.eat.DeliciousActivity;
import com.wisesoft.traveltv.ui.play.AmusementActivity;
import com.wisesoft.traveltv.ui.play.AmusementDetailActivity;
import com.wisesoft.traveltv.ui.play.AmusementMapActivity;
import com.wisesoft.traveltv.ui.stay.StayActivity;
import com.wisesoft.traveltv.ui.traffic.TrafficActivity;
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

        mGalleryCf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, AmusementDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, beanList.get(position));
                pushActivity(intent, false);
            }
        });

        mGalleryCf.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode){
                    case KeyEvent.KEYCODE_ENTER:
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                        if(event.getAction()==KeyEvent.ACTION_UP){
                            Intent intent = new Intent(HomeActivity.this, AmusementDetailActivity.class);
                            intent.putExtra(Constans.ITEM_BEAN, beanList.get(mGalleryCf.getScrollPosition()));
                            pushActivity(intent, false);
                            return true;
                        }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        /*-----测试数据-------*/
        beanList = DataEngine.getLandingBanner();
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
                //pushActivity(AmusementActivity.class);
                pushActivity(AmusementMapActivity.class);
                break;
            case R.id.m_stay_rb:
                pushActivity(StayActivity.class);
                break;
            case R.id.m_eat_rb:
                pushActivity(DeliciousActivity.class);
                break;
            case R.id.m_traffic_rb:
                pushActivity(TrafficActivity.class);
                break;
            case R.id.m_search_rb:
            case R.id.m_settings_rb:
                toast("Wait For Codding...");
                break;
        }
    }
}
