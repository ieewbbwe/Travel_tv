package com.wisesoft.traveltv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
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
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.FilterBean;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVIconView;
import com.wisesoft.traveltv.ui.view.weight.pop.TVFilterView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.10.20
 * Describe：分类-列表
 */
public class ProjectListActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_eat_tiv)
    TVIconView mEatTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    /*   @Bind(R.id.m_sort_tiv)
       TVIconView mSortTiv;*/
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_content_trv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_filter_tfv)
    TVFilterView mFilterTfv;
    @Bind(R.id.m_head1_iv)
    ImageView mHead1Iv;
    @Bind(R.id.m_head2_iv)
    ImageView mHead2Iv;
    @Bind(R.id.m_content_sv)
    ScrollView mContentSv;

    private StayAdapter mAdapter;
    private List<ItemInfoBean> items;
    private DataBaseDao mBaseDao;
    private List<ItemInfoBean> recommendList;
    private String mPageType;

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
        mReturnTiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popActivity();
            }
        });
        mFilterTfv.setOnItemClickListener(new com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, FilterBean parentFilter, FilterBean childFilter) {
                //toast("点击了 " + parentFilter.getName() + ":" + childFilter.getName());
                items = mBaseDao.getItemInfos(Constans.TYPE_STAY, 10);
                Collections.shuffle(items);
                mAdapter.setDataList(items);

            }
        });
        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.m_head1_iv:
                        case R.id.m_head2_iv:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 4f);
                    }
                }
                mFocusBorder.setVisible(false);
                return null;
            }
        });
        mHead1Iv.setOnClickListener(this);
        mHead2Iv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPageType = getIntent().getStringExtra(Constans.ARG_PAGE_TYPE);

        showNowNav(mPageType);

        mBaseDao = new DataBaseDao(this);
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
    }

    private void showNowNav(String mPageType) {
        int imgRes;
        if (mEatTiv != null) {
            switch (mPageType) {
                case Constans.TYPE_PLAY:
                    imgRes = R.mipmap.ic_home_play;
                    break;
                case Constans.TYPE_EAT:
                    imgRes = R.mipmap.ic_home_eat;
                    break;
                case Constans.TYPE_STAY:
                default:
                    imgRes = R.mipmap.ic_home_stay;
                    break;
            }
            mEatTiv.setImageResource(imgRes);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_return_tiv:
                //popActivity();
                break;
            case R.id.m_head1_iv:
                jumpToDetail(recommendList.get(0));
                break;
            case R.id.m_head2_iv:
                jumpToDetail(recommendList.get(1));
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (mHead1Iv.isFocused() || mHead2Iv.isFocused()) {
                        if (mListRlv != null) {
                            mListRlv.requestFocus();
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
                                mHead2Iv.requestFocus();
                            } else {
                                mHead2Iv.requestFocus();
                            }
                            return true;
                        }
                    }
                    return super.dispatchKeyEvent(event);
            }
        }
        return super.dispatchKeyEvent(event);
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

}
