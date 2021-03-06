package com.wisesoft.traveltv.ui.stay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.CollectionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.helper.GlideImageLoader;
import com.wisesoft.traveltv.model.temp.ImageBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.youth.banner.Banner;
import com.youth.banner.transformer.CubeOutTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ImageDetailActivity extends NActivity {

  /*  @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;*/
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
   /* @Bind(R.id.m_grade_tv)
    TextView mGradeTv;*/
   /* @Bind(R.id.m_phone_tv)
    TextView mPhoneTv;*/
    @Bind(R.id.m_address_tv)
    TextView mAddressTv;
    @Bind(R.id.m_image_display_bn)
    Banner mImageDisplayBn;            //图片展示位
    @Bind(R.id.m_content_fl)
    FrameLayout mImgContentFl;
    @Bind(R.id.m_grade_rb)
    RatingBar mGradeRb;

    private List<ImageBean> mImgList = new ArrayList<>();
    private ItemInfoBean mItemInfo;
    private String[] mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        restRatingHeight();
        //默认显示三张图片
        /*mImgList.add(new ImageBean("file:///android_asset/default1.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default2.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default3.jpg"));*/
        //设置图片加载器
        mImageDisplayBn.setImageLoader(new GlideImageLoader());
        //设置图片集合
        // mImageDisplayBn.setImages(mImgList);
        mImageDisplayBn.setBannerAnimation(CubeOutTransformer.class);
        mImageDisplayBn.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        //  mImageDisplayBn.start();

    }

    @Override
    protected void initListener() {
       /* mReturnTiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popActivity();
            }
        });*/
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mItemInfo = (ItemInfoBean) intent.getSerializableExtra(Constans.ITEM_BEAN);
        mImages = intent.getStringArrayExtra(Constans.ITEM_RECOMMEND_IMG);

        if (null != mItemInfo) {
            showItemInfo(mItemInfo);
        }
    }

    private void showItemInfo(ItemInfoBean mItemInfo) {
        mTitleTv.setText(mItemInfo.getName());
        //mGradeTv.setText(mItemInfo.getGradeStr());
        mAddressTv.setText(mItemInfo.getAddressStr());
        //mPhoneTv.setText(mItemInfo.getPhoneStr());
        //mImgList = mItemInfo.getImageList();
        mImgList.add(0,new ImageBean(mItemInfo.getImgUrl()));
        for(String url:mImages){
            mImgList.add(new ImageBean(url));
        }
        if (CollectionUtils.isNotEmpty(mImgList)) {
            mImageDisplayBn.update(mImgList);
            mImageDisplayBn.start();
        }

    /*    //虚化背景
        Glide.with(this).load(mItemInfo.getImgUrl())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        subscriber.onNext(BitmapUtils.blurImageAmeliorate(
                                BitmapUtils.ratio(resource,getScreenWidth(),getScreenHeight())
                        ));
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                mImgContentFl.setBackground(new BitmapDrawable(bitmap));
                            }
                        });
            }
        });*/
    }

    /**
     * 重置星星布局高度
     */
    private void restRatingHeight() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_rate_solid);
        int startHeight = bmp.getHeight();
        if (startHeight != 0) {
            ViewGroup.LayoutParams lp = mGradeRb.getLayoutParams();
            lp.height = startHeight;
            mGradeRb.setLayoutParams(lp);
        }
    }

    @Override
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
    }
}
