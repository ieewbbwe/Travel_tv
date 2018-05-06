package com.wisesoft.traveltv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.response.BaseResponse;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.NApplication;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.StayAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.ui.view.TVIconView;
import com.wisesoft.traveltv.ui.view.TVScrollView;
import com.wisesoft.traveltv.ui.view.weight.pop.TVFilterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mxh on 2017.10.20
 * Describe：分类-列表
 */
public class ProjectListActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_eat_tiv)
    TVIconView mNowTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    /*   @Bind(R.id.m_sort_tiv)
       TVIconView mSortTiv;*/
    @Bind(R.id.m_recomend_tiv)
    TVIconView mRecommendTiv;
    @Bind(R.id.m_return_top_tiv)
    TVIconView mReturnTop;
    @Bind(R.id.m_content_trv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_filter_tfv)
    TVFilterView mFilterTfv;
    @Bind(R.id.m_head1_iv)
    ImageView mHead1Iv;
    @Bind(R.id.m_head2_iv)
    ImageView mHead2Iv;
    @Bind(R.id.m_title1_tv)
    TextView mTitle1Tv;
    @Bind(R.id.m_title2_tv)
    TextView mTitle2Tv;
    @Bind(R.id.m_head1_cont)
    View mHead1Cont;
    @Bind(R.id.m_head2_cont)
    View mHead2Cont;

    @Bind(R.id.m_content_sv)
    TVScrollView mContentSv;

    private StayAdapter mAdapter;
    private List<ItemInfoBean> items = new ArrayList<>();
    private DataBaseDao mBaseDao;
    private List<ItemInfoBean> recommendList;
    private String mPageType;

    private int page = 1;
    private int limit = 9;
    private DataBaseDao mDao;
    private boolean isLoading;
    private Map<String, String> filterMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
    }

    @Override
    protected void initComp() {
        mBaseDao = new DataBaseDao(this);
        initBorder();
        ButterKnife.bind(this);
        mListRlv.setLayoutManager(new V7GridLayoutManager(this, 3));
        mListRlv.setSpacingWithMargins(20, 20);
        mAdapter = new StayAdapter(this);
    }

    @Override
    protected void initListener() {
        mListRlv.setOnItemListener(new SimpleOnItemListener() {
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

        mListRlv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });
        mFilterTfv.setOnItemClickListener(new com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, InitDataBean parentFilter, InitDataBean childFilter) {
                //toast("点击了 " + parentFilter.getName() + ":" + childFilter.getName());
               /* items = mBaseDao.getItemInfos(Constans.TYPE_STAY, 10);
                Collections.shuffle(items);
                mAdapter.setDataList(items);*/
                filterMap.put(parentFilter.getName(), childFilter.getCode());
                refresh();
            }
        });
        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.m_head1_cont:
                        case R.id.m_head2_cont:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 4f);
                    }
                }
                mFocusBorder.setVisible(false);
                return null;
            }
        });

        mContentSv.setOnScrollChangedListener(new TVScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChange(int l, int t, int ol, int ot) {
                mReturnTop.setVisibility((t >= getScreenHeight() / 2) ? View.VISIBLE : View.GONE);
                if (!mContentSv.canScrollVertically(1) && !isLoading) {
                    requestMore();
                }
            }
        });

        mListRlv.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                if (hasMore()) {
                    Lg.d("picher", "加载更多");
                    //page += page;
                    //requestData();
                    return true;
                }
                return false;
            }
        });

        mHead1Cont.setOnClickListener(this);
        mHead2Cont.setOnClickListener(this);
        mRecommendTiv.setOnClickListener(this);
        mReturnTop.setOnClickListener(this);
        mSearchTiv.setOnClickListener(this);
    }

    private void refresh() {
        page = 1;
        items.clear();
        mAdapter.setDataList(items);
        mListRlv.setAdapter(mAdapter);
        requestData();
    }

    private void requestMore() {
        page++;
        isLoading = true;
        requestData();
    }

    private boolean hasMore() {
        return items.size() >= limit * page;
    }

    @Override
    protected void initData() {
        mDao = new DataBaseDao(this);
        mPageType = getIntent().getStringExtra(Constans.ARG_PAGE_TYPE);
        showNowNav(mPageType);

        requestData();
        requestRecommend();
        mFilterTfv.setFilterList(mDao.getPageFilter(mPageType));

        /*mBaseDao = new DataBaseDao(this);
        items = mBaseDao.getItemInfos(mPageType, 10);
        items.addAll(mBaseDao.getItemInfos(30));
        Collections.shuffle(items);
        mAdapter.setDataList(items);
        mListRlv.setAdapter(mAdapter);

        mFilterTfv.setFilterList(DataEngine.getFilterData(mPageType));

        recommendList = mBaseDao.getRecommendInfo(mPageType);
        Collections.shuffle(recommendList);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(recommendList.get(0).getImgUrl(), mHead1Iv);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(recommendList.get(1).getImgUrl(), mHead2Iv);
        mTitle1Tv.setText(recommendList.get(0).getName());
        mTitle2Tv.setText(recommendList.get(1).getName());*/
    }

    private void showNowNav(String mPageType) {
        int imgRes;
        int textRes;
        if (mNowTiv != null) {
            switch (mPageType) {
                case Constans.TYPE_PLAY:
                    imgRes = R.mipmap.ic_home_play;
                    textRes = R.string.label_home_play;
                    break;
                case Constans.TYPE_EAT:
                    imgRes = R.mipmap.ic_home_eat;
                    textRes = R.string.label_home_eat;
                    break;
                case Constans.TYPE_STAY:
                default:
                    imgRes = R.mipmap.ic_home_stay;
                    textRes = R.string.label_home_stay;
                    break;
            }
            mNowTiv.setImageResource(imgRes);
            mNowTiv.setText(textRes);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_search_tiv:
                pushActivity(SearchResultActivity.class);
                break;
            case R.id.m_recomend_tiv:
                Intent intent = new Intent(this,RecommendActivity.class);
                intent.putExtra(Constans.ARG_PAGE_TYPE,mPageType);
                pushActivity(intent);
                break;
            case R.id.m_return_top_tiv:
                mHead1Cont.requestFocus();
                //mListRlv.setSelection(0);
                //mContentSv.scrollTo(0, 0);
                break;
            case R.id.m_head1_cont:
                if (recommendList != null) {
                    jumpToDetail(recommendList.get(0));
                }
                break;
            case R.id.m_head2_cont:
                if (recommendList != null && recommendList.size() > 0) {
                    jumpToDetail(recommendList.get(1));
                }
                break;
        }
    }

    public void requestData() {
        String area, star, sight, food_type, price;
        Float p_h, p_low;
        p_h = p_low = 0f;
        area = sight = food_type = star = price = "";
        for (String item : filterMap.keySet()) {
            switch (item) {
                case "景观类型":
                    if (filterMap.get(item).equals("0")) {
                        sight = "";
                    } else {
                        sight = filterMap.get(item);
                    }
                    break;
                case "酒家类型":
                    if (filterMap.get(item).equals("0")) {
                        food_type = "";
                    } else {
                        food_type = filterMap.get(item);
                    }
                    break;
                case "区域":
                    if (filterMap.get(item).equals("0")) {
                        area = "";
                    } else {
                        area = filterMap.get(item);
                    }
                    break;
                case "星级":
                    if (filterMap.get(item).equals("0")) {
                        star = "";
                    } else {
                        star = filterMap.get(item);
                    }
                    break;
                case "价格":
                    if (filterMap.get(item).equals("0")) {
                        price = "";
                        p_h = p_low = 0f;
                    } else {
                        price = filterMap.get(item);
                        String[] ps;
                        price = mBaseDao.getPriceStr(price);
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
                    }

                    break;
                default:
            }
        }

        ApiFactory.getTravelApi().getProductList(mPageType, area, star, sight, food_type, p_h, p_low, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        isLoading = false;
                        if (CollectionUtils.isNotEmpty(response.body().getResponse())) {
                            updateUI(response.body().getResponse());
                        } else {
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
        ApiFactory.getTravelApi().getRecommend(mPageType, 2, 1)
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
        recommendList = response;
        if (CollectionUtils.isNotEmpty(response) && response.size() >= 2) {
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(recommendList.get(0).getImgUrl(), mHead1Iv);
            mTitle1Tv.setText(recommendList.get(0).getName());
            ImageLoadFactory.getInstance().getImageLoadHandler()
                    .displayImage(recommendList.get(1).getImgUrl(), mHead2Iv);
            mTitle2Tv.setText(recommendList.get(1).getName());
        }
    }

    private void updateUI(List<ItemInfoBean> response) {
        items.addAll(response);
        mAdapter.setDataList(items);
        mListRlv.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        NApplication.getRefWatcher(this).watch(this);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mHead1Cont.isFocused() || mHead2Cont.isFocused()) {
                        if (mListRlv != null) {
                            mListRlv.setSelection(0);
                        }
                        return true;
                    }
                    return super.dispatchKeyEvent(event);
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (mListRlv != null && mListRlv.hasFocus()) {
                        int focusPos = mListRlv.getLayoutManager()
                                .getPosition(mListRlv.getFocusedChild());
                        if (focusPos == 0 || focusPos == 1 || focusPos == 2) {
                            if (mContentSv.canScrollVertically(-1)) {
                                mContentSv.scrollTo(0, 0);
                                mHead2Cont.requestFocus();
                            } else {
                                mHead2Cont.requestFocus();
                            }
                            return true;
                        }
                    }
                    return super.dispatchKeyEvent(event);
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
