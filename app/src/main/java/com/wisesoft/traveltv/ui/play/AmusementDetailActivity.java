package com.wisesoft.traveltv.ui.play;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.BitmapUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.TvVerticalScrollView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.RecommendAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.stay.ImageDetailActivity;
import com.wisesoft.traveltv.ui.view.TVControlView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AmusementDetailActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_main_iv)
    ImageView mMainIv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
    @Bind(R.id.m_evaluate_tv)
    TextView mEvaluateTv;
    @Bind(R.id.m_introduce_tv)
    TextView mIntroduceTv;
    @Bind(R.id.m_any_info)
    TextView mAnyInfo;
    @Bind(R.id.m_price_time_tv)
    TextView mPriceTimeTv;
    @Bind(R.id.m_play_tvc)
    TVControlView mPlayTvc;
    @Bind(R.id.m_comment_tvc)
    TVControlView mCommentTvc;
    @Bind(R.id.m_recommend_rlv)
    TvRecyclerView mRecommendRlv;
    @Bind(R.id.m_content_sv)
    TvVerticalScrollView mContentSv;

    private RecommendAdapter mAdapter;

    //推荐信息
    private List<ItemInfoBean> mRecommendBeans;
    //本页的条目对象
    private ItemInfoBean mItemInfoBean;
    private FrameLayout mContentLl;
    private Bitmap mBlueBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amsuement_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        mContentLl = (FrameLayout) findViewById(R.id.contentfl);
        initBorder();
        mAdapter = new RecommendAdapter(this);
        mRecommendRlv.setAdapter(mAdapter);
        mRecommendRlv.setSelectedItemAtCentered(true);
        //设置横向间距
        mRecommendRlv.setSpacingWithMargins(0, 20);
    }

    public void initBorder() {
        mFocusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f) //阴影宽度(方式二)
                .shadowColor(getResources().getColor(R.color.colorPrimary)) //阴影颜色
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2f) //边框宽度
                .borderColor(getResources().getColor(R.color.white)) //边框颜色
                .build(this);
    }

    @Override
    protected void initListener() {
        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.m_play_tvc:
                        case R.id.m_comment_tvc:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 4f);
                    }
                }
                mFocusBorder.setVisible(false);
                return null;
            }
        });
        mRecommendRlv.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, mAdapter.getItemObject(position));
                pushActivity(intent, false);
            }
        });
        mPlayTvc.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mItemInfoBean = (ItemInfoBean) getIntent().getSerializableExtra(Constans.ITEM_BEAN);
        if (mItemInfoBean != null) {
            showItemDetail(mItemInfoBean);
            showRecommendView(mItemInfoBean.getType());
        }
    }

    private void showItemDetail(@NonNull ItemInfoBean mItemInfoBean) {
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(mItemInfoBean.getImgUrl(), mMainIv);
        mTitleTv.setText(mItemInfoBean.getName());
        mEvaluateTv.setText(mItemInfoBean.getGradeStr());
        mIntroduceTv.setText(mItemInfoBean.getIntroduceStr());
        mAnyInfo.setText(mItemInfoBean.getAddressStr());
        mPriceTimeTv.setText(mItemInfoBean.getPriceTimeStr());
        //虚化背景
        Glide.with(this).load(mItemInfoBean.getImgUrl())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        mBlueBt = BitmapUtils.blurImageAmeliorate(
                                BitmapUtils.ratio(resource, getScreenWidth(), getScreenHeight())
                        );
                        subscriber.onNext(mBlueBt);
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                mContentLl.setBackground(new BitmapDrawable(mBlueBt));
                            }
                        });
            }
        });

        //TODO 暂时只有酒店类型才能播视屏
        /*if(!mItemInfoBean.getType().equals(Constans.TYPE_STAY)){
            mPlayTvc.setText("看图");
        }*/
    }

    /**
     * 展示推荐信息
     *
     * @param type 推荐类型参数
     */
    private void showRecommendView(String type) {
        if(type.equals(Constans.TYPE_EAT)){
            //默认查询10条，不够则循环
            mRecommendBeans = DataEngine.getHotelFoodInfo(mItemInfoBean.getHotel_id());
        }else{
            mRecommendBeans = DataEngine.getRecommendInfo(type, 10);
        }
        Collections.shuffle(mRecommendBeans);
        mAdapter.setDataList(mRecommendBeans);
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mPlayTvc.isFocused() || mCommentTvc.isFocused()) {
                    if (mRecommendRlv != null) {
                        mContentSv.scrollTo(0, mContentSv.getHeight());
                        mRecommendRlv.requestFocus();
                    }
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!mPlayTvc.isFocused()) {
                    if (mContentSv.canScrollVertically(-1)) {
                        mContentSv.scrollTo(0, 0);
                        mPlayTvc.requestFocus();
                    } else {
                        mPlayTvc.requestFocus();
                    }
                }
                return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_play_tvc:
                Intent intent;
                if (mItemInfoBean.getType().equals(Constans.TYPE_STAY)) {
                    intent = new Intent(AmusementDetailActivity.this, PlayVideoActivity.class);
                } else {
                    intent = new Intent(AmusementDetailActivity.this, ImageDetailActivity.class);
                }
                intent.putExtra(Constans.ITEM_BEAN, mItemInfoBean);
                pushActivity(intent, false);
                break;
            case R.id.m_comment_tvc:
                toast("点个赞");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBlueBt != null) {
            mBlueBt.recycle();
            mBlueBt = null;
        }
    }
}
