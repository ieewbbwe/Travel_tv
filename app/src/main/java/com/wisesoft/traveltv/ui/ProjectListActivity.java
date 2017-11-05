package com.wisesoft.traveltv.ui;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
    }

    @Override
    protected void initComp() {
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
                Lg.d("picher",""+mListRlv.getLastVisiblePosition());
                mReturnTop.setVisibility((t >= getScreenHeight() / 2) ? View.VISIBLE : View.GONE);
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
        ApiFactory.getTravelApi().getProductList(mPageType, limit, page)
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

    public void jumpToDetail(ItemInfoBean itemObject) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra(Constans.ITEM_BEAN, itemObject);
        pushActivity(intent, false);
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
