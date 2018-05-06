package com.wisesoft.traveltv.ui.change.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.response.BaseResponse;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NFragement;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.HotRecommendAdapter;
import com.wisesoft.traveltv.adapter.TodayRecommendAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.net.request.ItemRequestModel;
import com.wisesoft.traveltv.ui.ProjectDetailActivity;
import com.wisesoft.traveltv.ui.change.ProjectDetailChangeActivity;
import com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener;
import com.wisesoft.traveltv.ui.view.weight.pop.TVFilterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static moe.codeest.enviews.ENDownloadView.DownloadUnit.B;

/**
 * Created by picher on 2018/1/6.
 * Describe：
 */

public abstract class ProductListFragment extends NFragement {

    private ItemRequestModel itemRequestModel;

    public abstract String getViewPage();

    @Bind(R.id.m_today_recommend_rlv)
    TvRecyclerView mFilterRlv;
    @Bind(R.id.m_hot_recommend_rlv)
    TvRecyclerView mRecommendRlv;
    @Bind(R.id.m_content_sv)
    ScrollView mContentSv;
    @Bind(R.id.m_filter_tfv)
    TVFilterView mFilterTfv;
    @Bind(R.id.m_empty_container)
    View mEmptyV;

    private TodayRecommendAdapter mFilterAdapter;
    private HotRecommendAdapter mRecommendAdapter;

    private List<ItemInfoBean> mFilterData = new ArrayList<>();
    private List<ItemInfoBean> mRecommendData = new ArrayList<>();
    private DataBaseDao mDao;
    private String mPageType;
    private int page = 1;
    private int limit = 9;
    private boolean isLoading;

    @Override
    protected int create() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this, v);
        mDao = new DataBaseDao(activity);
        itemRequestModel = new ItemRequestModel();

        //初始化筛选
        mFilterRlv.setLayoutManager(new V7LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false));
        mFilterAdapter = new TodayRecommendAdapter(activity);
        mFilterAdapter.setDataList(mFilterData);
        mFilterRlv.setAdapter(mFilterAdapter);
        mFilterRlv.setSpacingWithMargins(0, 12);
        mFilterRlv.setSelectedItemAtCentered(true);
        //初始化本周热门
        mRecommendRlv.setLayoutManager(new V7LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false));
        mRecommendAdapter = new HotRecommendAdapter(activity);
        mRecommendRlv.setAdapter(mRecommendAdapter);
        mRecommendRlv.setSpacingWithMargins(0, 12);
        mRecommendRlv.setSelectedItemAtCentered(true);

    }

    @Override
    protected void initListener() {
        mFilterRlv.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });

        mRecommendRlv.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });

        mFilterTfv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View v, InitDataBean parentFilter, InitDataBean childFilter) {
                setRequestData(parentFilter, childFilter);
                refresh();
            }
        });

        mFilterRlv.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                if (hasMore()) {
                    Log.d("picher","加载更多！");
                    requestMore();
                    return true;
                }
                return false;
            }
        });

        mFilterAdapter.setOnItemClickListener(new com.github.jdsjlzx.interfaces.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mFilterAdapter.getDataList().get(position));
            }
        });

        mRecommendAdapter.setOnItemClickListener(new com.github.jdsjlzx.interfaces.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mRecommendAdapter.getDataList().get(position));
            }
        });

    }

    private void jumpToDetail(ItemInfoBean itemInfoBean) {
        Intent intent = new Intent(activity, ProjectDetailChangeActivity.class);
        intent.putExtra(Constans.ITEM_BEAN, itemInfoBean);
        pushActivity(intent, false);
    }

    private void requestMore() {
        page++;
        isLoading = true;
        requestData();
    }

    private boolean hasMore() {
        return mFilterData.size() >= limit * page;
    }

    private void setRequestData(InitDataBean parentFilter, InitDataBean childFilter) {
        if (itemRequestModel != null) {
            switch (parentFilter.getId_core()) {
                case Constans.FILTER_DATABASE_AREA:
                    itemRequestModel.setArea(childFilter.getCode());
                    break;
                case Constans.FILTER_DATABASE_STAR:
                    itemRequestModel.setStar(childFilter.getCode());
                    break;
                case Constans.FILTER_DATABASE_PRICE:
                    if(childFilter.getCode().equals("")){
                        itemRequestModel.setP_h(0);
                        itemRequestModel.setP_low(0);
                    }else{
                        String[] ps;
                        float p_low = 0,p_h = 0;
                        String price = childFilter.getName();
                        if (!TextUtils.isEmpty(price)) {
                            if (price.contains("~")) {
                                ps = price.split("~");
                                p_low = Float.valueOf(ps[0]);
                                p_h = Float.valueOf(ps[1].split("元")[0]);
                            } else if (price.contains("元以上")) {
                                ps = price.split("元以上");
                                p_low = Float.valueOf(ps[0]);
                                p_h = 0f;
                            } else if (price.contains("元以下")) {
                                ps = price.split("元以下");
                                p_low = 0f;
                                p_h = Float.valueOf(ps[0]);
                            }
                        }
                        itemRequestModel.setP_low(p_low);
                        itemRequestModel.setP_h(p_h);
                    }
                    break;
                case Constans.FILTER_DATABASE_SLIGHT:
                    itemRequestModel.setSight(childFilter.getCode());
                    break;
                case Constans.FILTER_DATABASE_FOOD_TYPE:
                    itemRequestModel.setFood_type(childFilter.getCode());
                    break;
            }
        }
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != getFocusBorder()) {
            getFocusBorder().onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    protected void initData() {
        /*-----填充一些假数据------*/
       /* List<ItemInfoBean> testBeans = DataEngine.getItemInfoFromLocal(activity,"item_json.json");
        mFilterData.addAll(testBeans);
        mRecommendData.addAll(testBeans.subList(0,5));
        mFilterAdapter.setDataList(mFilterData);
        mRecommendAdapter.setDataList(mRecommendData);*/
        /*-------------------*/
        mPageType = getViewPage();
        mFilterTfv.setFilterList(mDao.getPageFilter(mPageType));
        requestData();
        requestRecommend();
    }

    private void refresh() {
        page = 1;
        mFilterData.clear();
        mFilterAdapter.setDataList(mFilterData);
        mFilterRlv.setAdapter(mFilterAdapter);
        mEmptyV.setVisibility(View.GONE);
        requestData();
    }

    public void requestData() {
        ApiFactory.getTravelApi().getProductList(mPageType, itemRequestModel.getArea(), itemRequestModel.getStar(), itemRequestModel.getSight(),
                itemRequestModel.getFood_type(), itemRequestModel.getP_h(), itemRequestModel.getP_low(), limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        isLoading = false;
                        if (CollectionUtils.isNotEmpty(response.body().getResponse())) {
                            updateUI(response.body().getResponse());
                        } else {
                            if(page == 1){
                                mEmptyV.setVisibility(View.VISIBLE);
                            }
                            toast("没有更多的数据了");
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        if (isLoading) {
                            isLoading = false;
                            page--;
                        }
                        toast(message);
                    }
                });
    }

    private void requestRecommend() {
        ApiFactory.getTravelApi().getRecommend(mPageType, 5, 1)
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
        mRecommendData.clear();
        mRecommendData.addAll(response);
        mRecommendAdapter.setDataList(mRecommendData);
        mRecommendRlv.setAdapter(mRecommendAdapter);
    }

    private void updateUI(List<ItemInfoBean> response) {
        mFilterData.addAll(response);
        mFilterAdapter.setDataList(mFilterData);
        //mFilterRlv.setAdapter(mFilterAdapter);
        mFilterAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mFilterRlv.hasFocus()) {
                        if (mContentSv.canScrollVertically(1)) {
                            mContentSv.scrollTo(0, mContentSv.getHeight());
                            mRecommendRlv.requestFocus();
                        } else {
                            mRecommendRlv.requestFocus();
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (mFilterRlv.hasFocus()) {
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
