package com.wisesoft.traveltv.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.android_mobile.core.base.BaseActivity;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.response.BaseResponse;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.GalleryAdapter;
import com.wisesoft.traveltv.adapter.RecommendListAdapter;
import com.wisesoft.traveltv.adapter.StayAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendActivity extends NActivity {

    private TvRecyclerView mRecommendRlv;
    private RecommendListAdapter mAdapter;
    private String mType;
    private List<ItemInfoBean> mDatas = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
    }

    @Override
    protected void initComp() {
        initBorder();
        mRecommendRlv = (TvRecyclerView) findViewById(R.id.m_project_recommend_rlv);
        mRecommendRlv.setLayoutManager(new V7GridLayoutManager(this, 2));
        mRecommendRlv.setSpacingWithMargins(20, 20);
        mAdapter = new RecommendListAdapter(this);

    }

    @Override
    protected void initListener() {
        mRecommendRlv.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                super.onItemSelected(parent, itemView, position);
                mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0));
            }

        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mAdapter.getDataList().get(position));
            }
        });

        mRecommendRlv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });
    }

    @Override
    protected void initData() {
        mType = getIntent().getStringExtra(Constans.ARG_PAGE_TYPE);
        if(!TextUtils.isEmpty(mType)){
            requestData();
        }
    }

    private void requestData() {
        ApiFactory.getTravelApi().getRecommend(mType, 10, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        updateUI(response.body().getResponse());
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        toast(message);
                    }
                });
    }

    private void updateUI(List<ItemInfoBean> response) {
        mDatas.addAll(response);
        mAdapter.setDataList(mDatas);
        mRecommendRlv.setAdapter(mAdapter);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(!mRecommendRlv.hasFocus()){
            mRecommendRlv.setSelection(0);
        }
        return super.dispatchKeyEvent(event);
    }
}
