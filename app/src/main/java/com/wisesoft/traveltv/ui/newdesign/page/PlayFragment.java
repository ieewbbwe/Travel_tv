package com.wisesoft.traveltv.ui.newdesign.page;

import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.HeaderNewDesignAdapter;
import com.wisesoft.traveltv.adapter.PlayHeaderSpannableAdapter;
import com.wisesoft.traveltv.model.HeaderItemModel;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVHeaderItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by picher on 2018/4/12.
 * Describe：
 */

public class PlayFragment extends BaseListFragment {

    /*private List<ItemInfoBean> mRecommendData;
    private TVHeaderItemView mTop1;
    private TVHeaderItemView mTop2;
    private TVHeaderItemView mTop3;
    private TVHeaderItemView mTop4;
    private TVHeaderItemView mTop5;*/
    private TvRecyclerView mPlayHeaderRv;
    private HeaderNewDesignAdapter mHeaderAdapter;
    private V7GridLayoutManager mPlayHeaderLayout;
    private List<HeaderItemModel> mHeaderItem = new ArrayList<>();

    @Override
    public int getHeaderLayout() {
        return R.layout.play_fragment_header_recycle;
    }

    @Override
    public void initHeader() {
        mPlayHeaderRv = (TvRecyclerView) findViewById(R.id.m_play_header_rv);
        mHeaderAdapter = new HeaderNewDesignAdapter(getActivity());
        mPlayHeaderLayout = new V7GridLayoutManager(getActivity(),3,LinearLayoutManager.VERTICAL,false);
        mPlayHeaderRv.setLayoutManager(mPlayHeaderLayout);
        mPlayHeaderRv.setAdapter(mHeaderAdapter);
        mPlayHeaderRv.setSpacingWithMargins(12,12);

       /* ------测试数据--------*/
        mHeaderItem.addAll(DataEngine.getTestHeaderData(5, mHomeTab));
        mHeaderAdapter.setDataList(mHeaderItem);


       /* mTop1 = (TVHeaderItemView) findViewById(R.id.m_top_1);
        mTop2 = (TVHeaderItemView) findViewById(R.id.m_top_2);
        mTop3 = (TVHeaderItemView) findViewById(R.id.m_top_3);
        mTop4 = (TVHeaderItemView) findViewById(R.id.m_top_4);
        mTop5 = (TVHeaderItemView) findViewById(R.id.m_top_5);
        mHeaderViews.add(mTop1);
        mHeaderViews.add(mTop2);
        mHeaderViews.add(mTop3);
        mHeaderViews.add(mTop4);
        mHeaderViews.add(mTop5);
        focusControl();*/

        getRecommendData();
    }

    /*private void focusControl() {
        getFocusBorder().boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.m_top_1:
                        case R.id.m_top_2:
                        case R.id.m_top_3:
                        case R.id.m_top_4:
                        case R.id.m_top_5:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 8);
                    }
                }
                return null; //返回null表示不使用焦点框框架
            }
        });
    }*/

  /*  @Override
    public boolean dispatchFragmentKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) { //不响应抬起事件 防止回掉两次
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    Lg.d("picher","FilterRv:"+mFilterTrv.hasFocus()+"haderCon:"+mHeaderContainer.hasFocus()+"bar:"+mAppbarAbl.hasFocus());
                    if (mHeaderContainer.hasFocus() && !mFilterTrv.hasFocus()) {
                        mFilterTrv.requestFocus();
                        mAppbarAbl.setExpanded(false);
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
            }
        }
        return super.dispatchFragmentKeyEvent(event);
    }*/

    @Override
    public void requestFocus() {
        mPlayHeaderRv.requestFocus();
    }

    @Override
    public void updateRecommendUI(List<ItemInfoBean> itemInfoBeans) {
       /* mRecommendData.addAll(itemInfoBeans);

        if (!CollectionUtils.isEmpty(mRecommendData)) {
            for (int i = 0; i < mHeaderViews.size(); i++) {
                if (mRecommendData.size() <= i) {
                    mHeaderViews.get(i).setData(mRecommendData.get(i));
                }
            }
        }*/

    }
}
