package com.wisesoft.traveltv.ui.play;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.RecommendAdapter;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVControlView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AmusementDetailActivity extends NActivity {

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
    ScrollView mContentSv;

    private RecommendAdapter mAdapter;

    private List<ItemInfoBean> mRecommendBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amsuement_detail);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        mAdapter = new RecommendAdapter(this);
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
        mRecommendRlv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    @Override
    protected void initData() {
        /*-------测试数据--------*/
        mRecommendBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mRecommendBeans.add(new ItemInfoBean());
        }
        mAdapter.setDataList(mRecommendBeans);
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCommentTvc.isFocused() || mPlayTvc.isFocused()) {
                    mRecommendRlv.requestFocus();
                    mContentSv.fullScroll(ScrollView.FOCUS_DOWN);
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mRecommendRlv.isFocused()) {
                    mContentSv.fullScroll(ScrollView.FOCUS_UP);
                    mPlayTvc.requestFocus();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
