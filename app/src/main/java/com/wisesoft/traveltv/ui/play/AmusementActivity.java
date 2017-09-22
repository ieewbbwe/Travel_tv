package com.wisesoft.traveltv.ui.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.CollectionUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemListAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVIconView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-玩
 */
public class AmusementActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_list_rlv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_play_tiv)
    TVIconView mPlayTiv;
    @Bind(R.id.m_banner_iv)
    ImageView mBannerIv;

    private List<ItemInfoBean> beanList = new ArrayList<>();
    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amusement);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        mAdapter = new ItemListAdapter(this);
    }

    @Override
    protected void initListener() {
        mReturnTiv.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        DataBaseDao mDao = new DataBaseDao(this);
        beanList = mDao.getItemInfos(Constans.TYPE_PLAY);
        Collections.shuffle(beanList);
        mAdapter = new ItemListAdapter(this);
        mAdapter.setDataList(beanList);
        mListRlv.setAdapter(mAdapter);
        mListRlv.setSelectedItemAtCentered(true);//条目居中

        if (CollectionUtils.isNotEmpty(beanList)) {
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(beanList.get(0).getImgUrl(), mBannerIv);
        }

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AmusementActivity.this, AmusementDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, mAdapter.getItemObject(position));
                pushActivity(intent, false);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_return_tiv:
                popActivity();
                break;
        }
    }
}
