package com.wisesoft.traveltv.ui.newdesign.page;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.response.BaseResponse;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.FilterSpannerAdapter;
import com.wisesoft.traveltv.adapter.ListGridAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.manager.ConvertManager;
import com.wisesoft.traveltv.manager.ProductManager;
import com.wisesoft.traveltv.model.FilterItemModel;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.net.request.ItemRequestModel;
import com.wisesoft.traveltv.ui.change.HomeTab;
import com.wisesoft.traveltv.ui.newdesign.BaseNewDesignFragment;
import com.wisesoft.traveltv.ui.view.CustomerAppbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by picher on 2018/4/12.
 * Describe：新设计的列表基类
 */

public abstract class BaseListFragment extends BaseNewDesignFragment implements View.OnFocusChangeListener {

    protected static int HEADER_SIZE = 5;
    @Bind(R.id.m_header_container)
    FrameLayout mHeaderContainer;
    @Bind(R.id.m_filter_trv)
    TvRecyclerView mFilterTrv;
    @Bind(R.id.m_list_trv)
    TvRecyclerView mListTrv;
    @Bind(R.id.m_app_bar_abl)
    CustomerAppbarLayout mAppbarAbl;
    @Bind(R.id.m_container_cdl)
    CoordinatorLayout mContainerCdl;
    @Bind(R.id.m_empty_container)
    View mEmptyV;

    private View mHeaderView;
    private FilterSpannerAdapter mFilterAdapter;
    private V7GridLayoutManager mFilterManager;
    private V7GridLayoutManager mListLayoutManager;
    //private ListNewDesignAdapter mListNewDesignAdapter;
    protected HomeTab mHomeTab;
    private ListGridAdapter mListAdapter;
    private DataBaseDao mDao;

    private int page = 1;
    private int limit = 9;
    private boolean isLoading;
    private ItemRequestModel itemRequestModel;

    //獲取頭部佈局
    public abstract int getHeaderLayout();

    public abstract void initHeader();

    public abstract void updateRecommendUI(List<ItemInfoBean> itemInfoBeans);

    private List<ItemInfoBean> mListData = new ArrayList<>();
    private List<FilterItemModel> mFilterData = new ArrayList<>();

    @Override
    protected int create() {
        return R.layout.fragment_new_design_list;
    }

    @Override
    protected void initComp() {
        if (getArguments() != null) {
            mHomeTab = (HomeTab) getArguments().getSerializable(ARG_HOME_TAB);
        }
        mDao = new DataBaseDao(activity);
        itemRequestModel = new ItemRequestModel();

        ButterKnife.bind(this, v);
        //初始化头部布局
        mHeaderView = LayoutInflater.from(getActivity()).inflate(getHeaderLayout(), null);
        mHeaderContainer.addView(mHeaderView);
        //初始化筛选布局
        mFilterManager = new V7GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false);
        mFilterTrv.setLayoutManager(mFilterManager);
        mFilterAdapter = new FilterSpannerAdapter(getActivity());
        mFilterTrv.setAdapter(mFilterAdapter);
        mFilterTrv.setSpacingWithMargins(16, 24);
        //初始化列表布局
        mListLayoutManager = new V7GridLayoutManager(getActivity(), 6);
        mListTrv.setLayoutManager(mListLayoutManager);
        //mListNewDesignAdapter = new ListNewDesignAdapter(getActivity());
        mListAdapter = new ListGridAdapter(getActivity());
        mListAdapter.setDatas(mListData);
        mListTrv.setAdapter(mListAdapter);
        mListTrv.setSpacingWithMargins(16, 16);

        //初始化每个类别的header
        initHeader();
    }

    @Override
    protected void initListener() {
        mListAdapter.setOnItemListener(new CommonRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {
            }

            @Override
            public void onItemClick(View itemView, int position) {
                jumpToDetail(mListAdapter.getItem(position));
            }
        });

        mFilterAdapter.setOnItemListener(new CommonRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {
                onMoveFocusBorder(itemView, 1f, 8);
            }

            @Override
            public void onItemClick(View itemView, int position) {
                InitDataBean dataBean = mFilterAdapter.getDataList().get(position).getFilterData();
                setRequestData(dataBean);
                refresh();
            }
        });

        mListTrv.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                if (hasMore()) {
                    Lg.d("picher", "加载更多！");
                    requestMore();
                    return true;
                }
                return false;
            }
        });

        mListTrv.setOnFocusChangeListener(this);
        mFilterTrv.setOnFocusChangeListener(this);

        //由于FocusChange 回掉问题 暂时使用全局监听器来完成逻辑
        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    if (newFocus.getParent() instanceof ViewGroup) {
                        getFocusBorder().setVisible(false);
                        switch (((ViewGroup) newFocus.getParent()).getId()) {
                            case R.id.m_header_rv:
                                if (!mAppbarAbl.isExpened()) {
                                    mAppbarAbl.setExpanded(true);
                                }
                                break;
                            case R.id.m_filter_trv:
                                getFocusBorder().setVisible(true);
                                break;
                            case R.id.m_list_trv:
                                if (mAppbarAbl.isExpened()) {
                                    mAppbarAbl.setExpanded(false);
                                }
                                break;
                        }

                    }
                }
            }
        });
    }

    private void refresh() {
        page = 1;
        mListData.clear();
        mEmptyV.setVisibility(View.GONE);
        mListTrv.setHasMoreData(true);
        mListTrv.setLoadingMore(false);
        getListData();
    }

    @Override
    protected void initData() {
        /*——————測試數據—————*/
        /*mFilterData.addAll(DataEngine.getTestFilterData(10,mHomeTab));
        mListData.addAll(DataEngine.getTestListData(20));
        mFilterAdapter.setDataList(mFilterData);
        mListAdapter.setDatas(mListData);*/
        //设置筛选栏数据
        if (mDao != null && mHomeTab != null) {
            mFilterData.clear();
            List<InitDataBean> dataBeans = mDao.getNewDesignFilter(ProductManager.Companion.getInstance().getPageType(mHomeTab));
            if (!CollectionUtils.isEmpty(dataBeans) && dataBeans.get(0) != null && !CollectionUtils.isEmpty(dataBeans.get(0).getChildBean())) {
                mFilterData.addAll(ConvertManager.getInstance()
                        .convertItemToFilterModel(dataBeans.get(0).getChildBean(), mHomeTab));
            }
            mFilterAdapter.setDataList(mFilterData);
        }
    }

    private void setRequestData(InitDataBean childFilter) {
        if (itemRequestModel != null) {
            if("全部".equals(childFilter.getName())){
                itemRequestModel = new ItemRequestModel();
                return;
            }
            switch (childFilter.getParent_id()) {
                case Constans.FILTER_DATABASE_AREA:
                    itemRequestModel.setArea(childFilter.getCode());
                    break;
                case Constans.FILTER_DATABASE_STAR:
                    itemRequestModel.setStar(childFilter.getCode());
                    break;
                case Constans.FILTER_DATABASE_PRICE:
                    if (childFilter.getCode().equals("")) {
                        itemRequestModel.setP_h(0);
                        itemRequestModel.setP_low(0);
                    } else {
                        String[] ps;
                        float p_low = 0, p_h = 0;
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
                //购物类型
                case Constans.FILTER_DATABASE_PAY_TYPE:
                    itemRequestModel.setPay_type(childFilter.getCode());
                    break;
                //娛樂類型
                case Constans.FILTER_DATABASE_FUN_TYPE:
                    itemRequestModel.setFun_type(childFilter.getCode());
                    break;
            }
        }
    }

    private void requestMore() {
        page++;
        isLoading = true;
        getListData();
    }

    private boolean hasMore() {
        return mListData.size() >= limit * page;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //TODO Focus 焦点没有回掉 故注释此方法 以后调查
       /* Lg.d("picher","焦点发生变化：header："+mHeaderContainer.hasFocus()+"->>filter："+mFilterTrv.hasFocus()+"->>list："+mListTrv.hasFocus());
        switch (v.getId()){
            case R.id.m_list_trv:
                if(hasFocus){
                    mFilterTrv.clearFocus();
                    mAppbarAbl.setExpanded(false);
                }
                break;
            case R.id.m_filter_trv:
                if(hasFocus){
                    mListTrv.clearFocus();
                    mHeaderContainer.clearFocus();
                }
                getFocusBorder().setVisible(hasFocus);
                break;
        }*/
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != getFocusBorder()) {
            getFocusBorder().onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    /**
     * 获取推荐数据
     */
    public void getRecommendData() {
        ApiFactory.getTravelApi().getRecommend(ProductManager.Companion.getInstance().getPageType(mHomeTab), HEADER_SIZE, 1)
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

    /**
     * 获取列表数据
     */
    public void getListData() {
        ApiFactory.getTravelApi().getProductList(ProductManager.Companion.getInstance().getPageType(mHomeTab),
                itemRequestModel.getArea(), itemRequestModel.getStar(),itemRequestModel.getPay_type(),itemRequestModel.getFun_type(),
                itemRequestModel.getSight(), itemRequestModel.getFood_type(), itemRequestModel.getP_h(), itemRequestModel.getP_low(), limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        isLoading = false;
                        if (CollectionUtils.isNotEmpty(response.body().getResponse())) {
                            updateListUI(response.body().getResponse());
                        } else {
                            if (page == 1) {
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

    public void updateListUI(List<ItemInfoBean> itemInfoBeans) {
        Lg.d("picher", mHomeTab + "列表數：" + itemInfoBeans.size());
        mListData.addAll(itemInfoBeans);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void requestFocus() {

    }

}
