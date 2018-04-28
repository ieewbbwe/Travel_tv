package com.wisesoft.traveltv.ui.newdesign.page;

import android.view.KeyEvent;
import android.view.View;

import com.android_mobile.core.utiles.Lg;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ListHeaderSpannableAdapter;
import com.wisesoft.traveltv.layoutManager.CustomerGridlayoutManager;
import com.wisesoft.traveltv.model.ItemTypeModel;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by picher on 2018/4/12.
 * Describe：
 */

public class PlayFragment extends BaseListFragment {

    private TvRecyclerView mPlayHeaderRv;
    private ListHeaderSpannableAdapter mHeaderAdapter;
    private CustomerGridlayoutManager mPlayHeaderLayout;
    private List<ItemTypeModel> mHeaderItem = new ArrayList<>();

    @Override
    public int getHeaderLayout() {
        return R.layout.play_fragment_header_recycle;
    }

    @Override
    public void initHeader() {
        mPlayHeaderRv = (TvRecyclerView) findViewById(R.id.m_header_rv);
        mHeaderAdapter = new ListHeaderSpannableAdapter(getActivity(),mPlayHeaderRv);
        mPlayHeaderLayout = new CustomerGridlayoutManager(getActivity());
        mPlayHeaderRv.setLayoutManager(mPlayHeaderLayout);
        mPlayHeaderRv.setSpacingWithMargins(12,12);

       /* ------测试数据--------*/
        mHeaderItem.addAll(DataEngine.getTestHeaderData(5, mHomeTab));
        mHeaderAdapter.setDatas(mHeaderItem);
        mPlayHeaderRv.setAdapter(mHeaderAdapter);

        //获取真实数据
        //getRecommendData();

        mHeaderAdapter.setOnItemListener(new CommonRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {
                //onMoveFocusBorder(itemView, 1.1f, 8);
            }

            @Override
            public void onItemClick(View itemView, int position) {
                Lg.d("picher","点击头部："+position);
            }
        });
    }

    @Override
    public boolean dispatchFragmentKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) { //不响应抬起事件 防止回掉两次
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    /*if (mHeaderContainer.hasFocus()) {
                        mFilterTrv.requestFocus();
                        mAppbarAbl.setExpanded(false);
                    }*/
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    /*if (mListTrv.hasFocus() && !mListTrv.canScrollVertically(-1)) {
                        mFilterTrv.requestFocus();
                        mAppbarAbl.setExpanded(true);
                    }*/
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
            }
        }
        return super.dispatchFragmentKeyEvent(event);
    }

    @Override
    public void requestFocus() {
        mPlayHeaderRv.requestFocus();
    }

    @Override
    public void updateRecommendUI(List<ItemInfoBean> itemInfoBeans) {

    }
}
