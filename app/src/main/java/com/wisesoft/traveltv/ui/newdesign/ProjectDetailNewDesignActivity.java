package com.wisesoft.traveltv.ui.newdesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.net.response.BaseResponse;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.TvVerticalScrollView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ProjectRecommendAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.ui.play.PlayVideoActivity;
import com.wisesoft.traveltv.ui.stay.ImageDetailActivity;
import com.wisesoft.traveltv.ui.view.CheckOverSizeTextView;
import com.wisesoft.traveltv.ui.view.TVControlView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProjectDetailNewDesignActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_main_iv)
    ImageView mMainIv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
    @Bind(R.id.m_evaluate_tv)
    TextView mEvaluateTv;
    @Bind(R.id.m_introduce_tv)
    CheckOverSizeTextView mIntroduceTv;
    @Bind(R.id.m_any_info)
    TextView mAnyInfo;
    @Bind(R.id.m_price_time_tv)
    TextView mPriceTimeTv;
    @Bind(R.id.m_tel_info)
    TextView mTelInfoTv;
    @Bind(R.id.m_grade_rb)
    RatingBar mGradeRb;
    @Bind(R.id.m_play_tvc)
    TVControlView mPlayTvc;
    @Bind(R.id.m_comment_tvc)
    TVControlView mCommentTvc;
    @Bind(R.id.m_traffic_tvc)
    TVControlView mTrafficTvc;
    @Bind(R.id.m_recommend_rlv)
    TvRecyclerView mRecommendRlv;
    @Bind(R.id.m_content_sv)
    TvVerticalScrollView mContentSv;

    private ProjectRecommendAdapter mAdapter;

    //推荐信息
    private List<ItemInfoBean> mRecommendBeans;
    //本页的条目对象
    private ItemInfoBean mItemInfoBean;
    private FrameLayout mContentLl;
    private Bitmap mBlueBt;
    private ProductDialog mProductDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_new_design);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        mContentLl = (FrameLayout) findViewById(R.id.contentfl);
        initBorder();
        restRatingHeight();
        mAdapter = new ProjectRecommendAdapter(this);
        mRecommendRlv.setAdapter(mAdapter);
        mRecommendRlv.setSelectedItemAtCentered(true);
        //设置横向间距
        mRecommendRlv.setSpacingWithMargins(0, 20);
    }

    /**
     * 重置星星布局高度
     */
    private void restRatingHeight() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_rate_solid);
        int startHeight = bmp.getHeight();
        if (startHeight != 0) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGradeRb.getLayoutParams();
            lp.height = startHeight;
            mGradeRb.setLayoutParams(lp);
        }
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
                        case R.id.m_traffic_tvc:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 8f);
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

        mRecommendRlv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击推荐产品 打开dialog
                ItemInfoBean infoBean = mAdapter.getItemObject(position);
                if(mProductDialog == null){
                    mProductDialog = new ProductDialog(ProjectDetailNewDesignActivity.this,R.style.dialog_no_border);
                }
                mProductDialog.show();
                mProductDialog.setData(infoBean);
            }
        });

        mPlayTvc.setOnClickListener(this);
        mCommentTvc.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mItemInfoBean = (ItemInfoBean) getIntent().getSerializableExtra(Constans.ITEM_BEAN);
        if (mItemInfoBean != null) {
            updateDetailUI(mItemInfoBean);
            //showRecommendView(mItemInfoBean.getType());
            requestDetail();
            requestRecommend();
        }
    }

    private void requestRecommend() {
        ApiFactory.getTravelApi().getProduceRecommend("" + mItemInfoBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Response<BaseResponse<List<ItemInfoBean>>>>bindToLifecycle())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        updateRecommendUI(response.body().getResponse());
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        toast(message);
                    }
                });

    }

    private void updateRecommendUI(List<ItemInfoBean> response) {
        if (CollectionUtils.isNotEmpty(response)) {
            mRecommendBeans = response;
            mAdapter.setDataList(mRecommendBeans);
        }

    }

    private void requestDetail() {
        ApiFactory.getTravelApi().getDetail("" + mItemInfoBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Response<BaseResponse<List<ItemInfoBean>>>>bindToLifecycle())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        if (CollectionUtils.isNotEmpty(response.body().getResponse())) {
                            updateDetailUI(response.body().getResponse().get(0));
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        toast(message);
                    }
                });
    }

    public void initBorder() {
        mFocusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f) //阴影宽度(方式二)
                .shadowColor(getResources().getColor(R.color.colorPrimary)) //阴影颜色
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2f) //边框宽度
                .borderColor(getResources().getColor(R.color.white)) //边框颜色
                .build(this);
    }

    private void updateDetailUI(@NonNull ItemInfoBean mItemInfoBean) {
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(mItemInfoBean.getImgUrl(), mMainIv);
        mTitleTv.setText(mItemInfoBean.getName());
        mGradeRb.setRating((float) mItemInfoBean.getGrade());
        mIntroduceTv.setText(mItemInfoBean.getIntroduceStr());
        mAnyInfo.setText(mItemInfoBean.getAddressStr());
        //mPriceTimeTv.setText(mItemInfoBean.getPriceTimeStr());
        mTelInfoTv.setText(mItemInfoBean.getPhoneStr());
        mTelInfoTv.setVisibility(TextUtils.isEmpty(mItemInfoBean.getTel_num()) ? View.GONE : View.VISIBLE);
        mPlayTvc.setText(TextUtils.isEmpty(mItemInfoBean.getVideo_path()) ? "看图" : "播放");
       /* try {
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
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }*/

        //判断介绍超出显示...
        if (mIntroduceTv.isOverSize()) {

        }
    }

    private void showRecommendView(String type) {
        if (type.equals(Constans.TYPE_EAT)) {
            //默认查询10条，不够则循环
            mRecommendBeans = DataEngine.getHotelFoodInfo(mItemInfoBean.getHotel_id());
        } else {
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
                if (mPlayTvc.isFocused() || mCommentTvc.isFocused() || mTrafficTvc.isFocused()) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_play_tvc:
                Intent intent;
                if (!TextUtils.isEmpty(mItemInfoBean.getVideo_path())) {
                    intent = new Intent(this, PlayVideoActivity.class);
                } else {
                    intent = new Intent(this, ImageDetailNewDesignActivity.class);
                    intent.putExtra(Constans.ITEM_RECOMMEND_IMG, (Serializable) mRecommendBeans);
                }
                intent.putExtra(Constans.ITEM_BEAN, mItemInfoBean);
                pushActivity(intent, false);
                break;
            case R.id.m_comment_tvc:
                toast("感谢您的赞！");
                break;
        }
    }

    private String[] getRecommendImages() {
        if(CollectionUtils.isNotEmpty(mRecommendBeans)){
            String[] strs = new String[mRecommendBeans.size()];
            for(int i=0;i<mRecommendBeans.size();i++){
                strs[i] = mRecommendBeans.get(i).getImgUrl();
            }
            return strs;
        }else{
            return new String[0];
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
