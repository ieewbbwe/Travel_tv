package com.wisesoft.traveltv.ui.play;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.Lg;
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
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.stay.ImageDetailActivity;
import com.wisesoft.traveltv.ui.view.TVControlView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amsuement_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        LinearLayout mContenLl = (LinearLayout) findViewById(R.id.m_content_ll);
        Lg.d("picher", "找到根布局？" + (mContenLl != null));
        initBorder();
        mAdapter = new RecommendAdapter(this);
        mAdapter.setDataList(DataEngine.getVideos(10));
        mRecommendRlv.setAdapter(mAdapter);
        mRecommendRlv.setSelectedItemAtCentered(true);
        //设置横向间距
        mRecommendRlv.setSpacingWithMargins(0, 10);
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
                onMoveFocusBorder(itemView, 1f, 0);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toast("点击了:" + position);
            }
        });
        mPlayTvc.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mItemInfoBean = (ItemInfoBean) getIntent().getSerializableExtra(Constans.ITEM_BEAN);
        if (mItemInfoBean != null) {
            showItemDetail(mItemInfoBean);
        }
    }

    private void showItemDetail(@NonNull ItemInfoBean mItemInfoBean) {
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(mItemInfoBean.getImgUrl(), mMainIv);
        mTitleTv.setText(mItemInfoBean.getName());
        mEvaluateTv.setText(mItemInfoBean.getGradeStr());
        mIntroduceTv.setText(mItemInfoBean.getIntroduceStr());
        mAnyInfo.setText(mItemInfoBean.getAnotherStr());

        //虚化背景
        Glide.with(this).load(mItemInfoBean.getImgUrl())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mMainIv.setImageDrawable(new BitmapDrawable(BitmapUtils.processBitmapBlurFast(resource)));
            }
        });

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
        Lg.print("picher", "" + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_play_tvc:
                pushActivity(ImageDetailActivity.class);
                break;
            case R.id.m_comment_tvc:
                toast("点个赞");
                break;
        }
    }
}
