package com.wisesoft.traveltv.ui.change.fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android_mobile.net.response.BaseResponse;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NFragement;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.GalleryAdapter;
import com.wisesoft.traveltv.adapter.HotRecommendAdapter;
import com.wisesoft.traveltv.adapter.TodayRecommendAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.BannerBean;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.ui.change.ProjectDetailChangeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by picher on 2018/1/6.
 * Describe：
 */

public class HomeFragment extends NFragement {

    @Bind(R.id.m_home_cover)
    FeatureCoverFlow mHomeCover;
    @Bind(R.id.m_today_recommend_rlv)
    TvRecyclerView mTodayRecommendRlv;
    @Bind(R.id.m_hot_recommend_rlv)
    TvRecyclerView mHotRecommendRlv;
    @Bind(R.id.m_content_sv)
    ScrollView mContentSv;

    private GalleryAdapter mGalleryAdapter;
    private List<ItemInfoBean> beanList;
    private TodayRecommendAdapter mTodayRecommendAdapter;
    private HotRecommendAdapter mHotRecommendAdapter;

    private List<ItemInfoBean> mTodayRecommentData = new ArrayList<>();
    private List<ItemInfoBean> mHotRecommentData = new ArrayList<>();

    @Override
    protected int create() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this, v);

        beanList = DataEngine.getLandingBanner();
        //初始化滑动图片块
        mGalleryAdapter = new GalleryAdapter(beanList, activity);
        mHomeCover.setAdapter(mGalleryAdapter);
        //初始化今日推荐
        mTodayRecommendRlv.setLayoutManager(new V7LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false));
        mTodayRecommendAdapter = new TodayRecommendAdapter(activity);
        mTodayRecommendRlv.setAdapter(mTodayRecommendAdapter);
        mTodayRecommendRlv.setSpacingWithMargins(0, 12);
        mTodayRecommendRlv.setSelectedItemAtCentered(true);
        //初始化本周热门
        mHotRecommendRlv.setLayoutManager(new V7LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false));
        mHotRecommendAdapter = new HotRecommendAdapter(activity);
        mHotRecommendRlv.setAdapter(mHotRecommendAdapter);
        mHotRecommendRlv.setSpacingWithMargins(0, 12);
        mHotRecommendRlv.setSelectedItemAtCentered(true);
    }

    @Override
    protected void initListener() {
        mTodayRecommendRlv.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });

        mHotRecommendRlv.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });

        mTodayRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mTodayRecommendAdapter.getDataList().get(position));

            }
        });

        mHotRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mHotRecommendAdapter.getDataList().get(position));

            }
        });
        mGalleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                jumpToDetail(mGalleryAdapter.getDataList().get(position));
            }
        });
    }

    private void jumpToDetail(ItemInfoBean itemInfoBean) {
        Intent intent = new Intent(activity, ProjectDetailChangeActivity.class);
        intent.putExtra(Constans.ITEM_BEAN, itemInfoBean);
        pushActivity(intent, false);
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != getFocusBorder()) {
            getFocusBorder().onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    protected void initData() {

        List<ItemInfoBean> testBeans = DataEngine.getItemInfoFromLocal(activity, "item_json.json");
        mHotRecommentData.addAll(testBeans);
        mTodayRecommentData.addAll(testBeans);

        mTodayRecommendAdapter.setDataList(mTodayRecommentData);
        mHotRecommendAdapter.setDataList(mHotRecommentData);

        requestBannerData();
        requestTodayRecommend();
    }

    private void requestTodayRecommend() {
        ApiFactory.getTravelApi().getRecommend("", 9, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        mTodayRecommentData.clear();
        mTodayRecommentData.addAll(response);
        mTodayRecommendAdapter.setDataList(mTodayRecommentData);

    }

    private void requestBannerData() {
        ApiFactory.getTravelApi().getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        updateBanner(response.body().getResponse());
                    }

                    @Override
                    public void onFailed(int code, String message) {

                    }
                });
    }

    private void updateBanner(List<ItemInfoBean> response) {
        beanList.clear();
        beanList.addAll(response);
        mHomeCover.setAdapter(mGalleryAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mTodayRecommendRlv.hasFocus()) {
                        if (mContentSv.canScrollVertically(1)) {
                            mContentSv.scrollTo(0, mContentSv.getHeight());
                            mHotRecommendRlv.requestFocus();
                        } else {
                            mHotRecommendRlv.requestFocus();
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (mTodayRecommendRlv.hasFocus()) {
                        if (mContentSv.canScrollVertically(-1)) {
                            mContentSv.fullScroll(ScrollView.FOCUS_UP);
                        }
                        getFocusBorder().setVisible(false);
                    }
                    break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
