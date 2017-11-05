package com.wisesoft.traveltv.ui.play;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemListAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.InitDataBean;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.weight.pop.TVFilterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-玩
 */
public class AmusementActivity extends NActivity implements View.OnClickListener {

    /*@Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_play_tiv)
    TVIconView mPlayTiv;
    @Bind(R.id.m_banner_iv)
    ImageView mBannerIv;*/
    @Bind(R.id.m_list_rlv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_filter_tfv)
    TVFilterView mFilterTfv;
    @Bind(R.id.m_head1_iv)
    ImageView mHead1Iv;
    @Bind(R.id.m_head2_iv)
    ImageView mHead2Iv;
    @Bind(R.id.m_content_sv)
    ScrollView mContentSv;

    private List<ItemInfoBean> beanList = new ArrayList<>();
    private List<ItemInfoBean> recommendList = new ArrayList<>();
    private ItemListAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amusement);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        mListRlv.setSpacingWithMargins(20, 20);
        mAdapter = new ItemListAdapter(this);
        gridLayoutManager = new V7GridLayoutManager(this, 3);
        mListRlv.setLayoutManager(gridLayoutManager);
        mListRlv.setSelectedItemAtCentered(true);//条目居中
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
        mListRlv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToDetail(mAdapter.getItemObject(position));
            }
        });
        mFilterTfv.setOnItemClickListener(new com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, InitDataBean parentFilter, InitDataBean childFilter) {
                //toast("点击了 " + parentFilter.getName() + ":" + childFilter.getName());
            }
        });

       /* gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                }
                return 1;
            }
        });*/
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
        DataBaseDao mDao = new DataBaseDao(this);
        beanList = mDao.getItemInfos(Constans.TYPE_PLAY);
        Collections.shuffle(beanList);

        mAdapter.setDataList(beanList);
        mListRlv.setAdapter(mAdapter);

        mFilterTfv.setFilterList(DataEngine.getPlayFilterData());

        recommendList = mDao.getRecommendInfo(Constans.TYPE_PLAY);
        Collections.shuffle(recommendList);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(recommendList.get(0).getImgUrl(),mHead1Iv);
        ImageLoadFactory.getInstance().getImageLoadHandler()
                .displayImage(recommendList.get(1).getImgUrl(),mHead2Iv);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_return_tiv:
                popActivity();
                break;
            case R.id.m_head1_iv:
                jumpToDetail(recommendList.get(0));
                break;
            case R.id.m_head2_iv:
                jumpToDetail(recommendList.get(1));
                break;
        }
    }

    public void jumpToDetail(ItemInfoBean itemObject) {
        Intent intent = new Intent(this, AmusementDetailActivity.class);
        intent.putExtra(Constans.ITEM_BEAN, itemObject);
        pushActivity(intent, false);
    }
}
