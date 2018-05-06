package com.wisesoft.traveltv.ui.newdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android_mobile.core.utiles.CollectionUtils;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ImageDetailAdapter;
import com.wisesoft.traveltv.adapter.ImageIndicatorAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailNewDesignActivity extends NActivity {

    /* @Bind(R.id.m_image_display_bn)
     Banner mImageDisplayBn;            //图片展示位*/
    @Bind(R.id.m_image_vp)
    ViewPager mImageVp;
    @Bind(R.id.m_indicator_trv)
    TvRecyclerView mIndicatorTrv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;

    private List<ItemInfoBean> mImgList = new ArrayList<>();
    private ItemInfoBean mItemInfo;
    private ImageIndicatorAdapter mImageIndicatorAdapter;
    private V7LinearLayoutManager mIndicatorManager;
    private ImageDetailAdapter imageDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail_new_design);
    }

    @Override
    protected void initComp() {
        initBorder();
        ButterKnife.bind(this);
        //默认显示三张图片
        /*mImgList.add(new ImageBean("file:///android_asset/default1.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default2.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default3.jpg"));*/
        //设置图片加载器
        /*mImageDisplayBn.setImageLoader(new GlideImageLoader());
        mImageDisplayBn.setBannerAnimation(DefaultTransformer.class);
        mImageDisplayBn.setDelayTime(5000);*/
        //banner设置方法全部调用完毕时最后调用
        //  mImageDisplayBn.start();

        mIndicatorManager = new V7LinearLayoutManager(this);
        mImageIndicatorAdapter = new ImageIndicatorAdapter(this);
        mIndicatorTrv.setSelectedItemAtCentered(true);
        mIndicatorTrv.setSpacingWithMargins(20, 20);
        mIndicatorManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mIndicatorTrv.setLayoutManager(mIndicatorManager);
        mIndicatorTrv.setAdapter(mImageIndicatorAdapter);

        imageDetailAdapter = new ImageDetailAdapter(this);
        mImageVp.setAdapter(imageDetailAdapter);
    }

    @Override
    protected void initListener() {
        mImageIndicatorAdapter.setOnItemListener(new CommonRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {
                onMoveFocusBorder(itemView, 1.05f, 0);
                mImageVp.setCurrentItem(position);
                mTitleTv.setText(mImageIndicatorAdapter.getItem(position).getName());
            }

            @Override
            public void onItemClick(View itemView, int position) {

            }
        });

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mItemInfo = (ItemInfoBean) intent.getSerializableExtra(Constans.ITEM_BEAN);
        mImgList = (List<ItemInfoBean>) intent.getSerializableExtra(Constans.ITEM_RECOMMEND_IMG);

        if (!CollectionUtils.isEmpty(mImgList)) {
            showItemInfo(mItemInfo);
        }
    }

    private void showItemInfo(ItemInfoBean mItemInfo) {
        mImgList.add(0, mItemInfo);
        /*for (String url : mImages) {
            mImgList.add(new ImageBean(url));
        }*/
        if (CollectionUtils.isNotEmpty(mImgList)) {
           /* mImageDisplayBn.update(mImgList);
            //mImageDisplayBn.setBannerStyle(BannerConfig.NUM_INDICATOR);
            mImageDisplayBn.updateBannerStyle(BannerConfig.NOT_INDICATOR);
            //mImageDisplayBn.start();*/
            imageDetailAdapter.setDatas(mImgList);
            mImageIndicatorAdapter.setDatas(mImgList);
            imageDetailAdapter.notifyDataSetChanged();
            mImageIndicatorAdapter.notifyDataSetChanged();
        }
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIndicatorTrv.requestFocus();
    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        if (mImageDisplayBn != null) {
            mImageDisplayBn.startAutoPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mImageDisplayBn != null) {
            mImageDisplayBn.stopAutoPlay();
        }
    }*/
}
