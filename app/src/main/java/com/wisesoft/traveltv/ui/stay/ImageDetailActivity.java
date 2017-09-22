package com.wisesoft.traveltv.ui.stay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.helper.GlideImageLoader;
import com.wisesoft.traveltv.model.ImageBean;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVIconView;
import com.youth.banner.Banner;
import com.youth.banner.transformer.RotateUpTransformer;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.m_image_display_bn)
    Banner mImageDisplayBn;            //图片展示位
    @Bind(R.id.m_content_fl)
    FrameLayout mImgContentFl;

    private List<ImageBean> mImgList = new ArrayList<>();
    private ItemInfoBean mItemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        //默认显示三张图片
        mImgList.add(new ImageBean("file:///android_asset/default1.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default2.jpg"));
        mImgList.add(new ImageBean("file:///android_asset/default3.jpg"));
        //设置图片加载器
        mImageDisplayBn.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mImageDisplayBn.setImages(mImgList);
        mImageDisplayBn.setBannerAnimation(RotateUpTransformer.class);
        mImageDisplayBn.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        mImageDisplayBn.start();

        /*Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File f = null;
                try {
                    f = Glide.with(mContext).load("file:///android_asset/default1.jpg")
                            .downloadOnly((int) getScreenWidth(), (int) getScreenHeight()).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(f);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        Lg.print("picher", "文件地址：" + file.getPath());
                    }
                });
        Lg.print("picher", "文件地址：" + (mImgContentLl == null));*/

    }

    @Override
    protected void initListener() {
        mReturnTiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popActivity();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mItemInfo = (ItemInfoBean) intent.getSerializableExtra(Constans.ITEM_BEAN);
        if (null != mItemInfo) {
            showItemInfo(mItemInfo);
        }
    }

    private void showItemInfo(ItemInfoBean mItemInfo) {
        mTitleTv.setText(mItemInfo.getName());
        mGradeTv.setText(mItemInfo.getGradeStr());
        mAddressTv.setText(mItemInfo.getAddressStr());
        mPhoneTv.setText(mItemInfo.getPhoneStr());
        //mImgList = mItemInfo.getImageList();
        mImgList.add(new ImageBean(mItemInfo.getImgUrl()));
        Lg.d("picher", "图片数：" + mImgList.size());
        if (CollectionUtils.isNotEmpty(mImgList)) {
            mImageDisplayBn.update(mImgList);
        }

        //虚化背景
        Glide.with(this).load(mItemInfo.getImgUrl())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap mBlueBt = BitmapUtils.progressBitmapPoxBlur(
                        resource,getScreenWidth(),getScreenHeight());
                mImgContentFl.setBackground(new BitmapDrawable(mBlueBt));
            }
        });
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
