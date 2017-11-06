package com.wisesoft.traveltv.ui.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android_mobile.core.utiles.Lg;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.stay.ImageDetailActivity;
import com.wisesoft.traveltv.ui.view.TVImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AmusementMapActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_zhaojun_tiv)
    TVImageView mZhaojunTiv;
    @Bind(R.id.m_drift_tiv)
    TVImageView mDriftTiv;
    @Bind(R.id.m_waterfall_tiv)
    TVImageView mWaterfallTiv;
    @Bind(R.id.m_fish_tiv)
    TVImageView mFishTiv;
    @Bind(R.id.m_dam_tiv)
    TVImageView mDamTiv;
    @Bind(R.id.m_family_tiv)
    TVImageView mFamilyTiv;
    @Bind(R.id.imageView)
    ImageView mContentIv;

    private DataBaseDao dataBaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amusement_map);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        //Glide.with(this).load(R.mipmap.ic_play_bg).crossFade().into(mContentIv);
    }

    @Override
    protected void initListener() {
        mZhaojunTiv.setOnClickListener(this);
        mDriftTiv.setOnClickListener(this);
        mWaterfallTiv.setOnClickListener(this);
        mDamTiv.setOnClickListener(this);
        mFishTiv.setOnClickListener(this);
        mFamilyTiv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        dataBaseDao = new DataBaseDao(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ImageDetailActivity.class);
        String name = "";
        switch (v.getId()){
            case R.id.m_zhaojun_tiv:
                name= "屈原";
                break;
            case R.id.m_dam_tiv:
                name= "百里荒";
                break;
            case R.id.m_family_tiv:
                name= "坛子岭";
                break;
            case R.id.m_fish_tiv:
                name= "画廊";
                break;
            case R.id.m_waterfall_tiv:
                name= "瀑布";
                break;
            case R.id.m_drift_tiv:
                name= "漂流";
                break;
        }
        ItemInfoBean mItemben = dataBaseDao.getItemInfoByName(name);
        Lg.print("picher",""+(mItemben==null));
        intent.putExtra(Constans.ITEM_BEAN, mItemben);

        pushActivity(intent, false);

    }
}
