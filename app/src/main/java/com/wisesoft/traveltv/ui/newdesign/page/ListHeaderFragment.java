package com.wisesoft.traveltv.ui.newdesign.page;

import android.util.Log;
import android.view.View;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.Utiles;
import com.owen.tvrecyclerview.TwoWayLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.adapter.CommonRecyclerViewAdapter;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ListHeaderSpannableAdapter;
import com.wisesoft.traveltv.layoutManager.CustomerGridlayoutManager;
import com.wisesoft.traveltv.manager.ConvertManager;
import com.wisesoft.traveltv.model.ItemTypeModel;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by picher on 2018/4/27.
 * Describe：带有header的通用List
 */

public class ListHeaderFragment extends BaseListFragment {

    private TvRecyclerView mPlayHeaderRv;
    private ListHeaderSpannableAdapter mHeaderAdapter;
    private CustomerGridlayoutManager mPlayHeaderLayout;
    private List<ItemTypeModel> mHeaderItem = new ArrayList<>();

    @Override
    public int getHeaderLayout() {
        return R.layout.list_header_fragment_header_recycle;
    }

    @Override
    public void initHeader() {
        mPlayHeaderRv = (TvRecyclerView) findViewById(R.id.m_header_rv);
        mHeaderAdapter = new ListHeaderSpannableAdapter(getActivity(),mPlayHeaderRv);
        mPlayHeaderLayout = new CustomerGridlayoutManager(getActivity());
        mPlayHeaderRv.setLayoutManager(mPlayHeaderLayout);
        mPlayHeaderRv.setSpacingWithMargins(28,28);
        adjustHeaderParamByType();

        mHeaderAdapter.setDatas(mHeaderItem);
        mPlayHeaderRv.setAdapter(mHeaderAdapter);

        //获取真实数据
        getRecommendData();
        //获取列表数据
        getListData();

        mHeaderAdapter.setOnItemListener(new CommonRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemSelected(View itemView, int position) {
                //onMoveFocusBorder(itemView, 1.1f, 8);
            }

            @Override
            public void onItemClick(View itemView, int position) {
                jumpToDetail(mHeaderAdapter.getItem(position).getFilterData());
            }
        });
    }

    /**
     * 根据Type不同设置不同的Header
     */
    private void adjustHeaderParamByType() {
        int columns = 1, rows = 1, heightDp = 0;
        TwoWayLayoutManager.Orientation orientation = TwoWayLayoutManager.Orientation.VERTICAL;
        List<ItemTypeModel> itemModels;
        if(mHomeTab != null){
            switch (mHomeTab){
                case TAB_PLAY:
                    columns = 3;
                    rows = 2;
                    HEADER_SIZE = 5;
                    heightDp = 500;
                    break;
                case TAB_EAT:
                    columns = 6;
                    rows = 2;
                    HEADER_SIZE = 9;
                    heightDp = 500;
                    break;
                case TAB_STAY:
                    columns = 6;
                    rows = 2;
                    HEADER_SIZE = 7;
                    heightDp = 500;
                    break;
                case TAB_PAY:
                case TAB_FUN:
                    columns = 3;
                    rows = 1;
                    HEADER_SIZE = 3;
                    heightDp = 250;
                    break;
            }
        }
        mPlayHeaderLayout.setNumColumns(columns);
        mPlayHeaderLayout.setNumRows(rows);
        mPlayHeaderLayout.setOrientation(orientation);
        mPlayHeaderRv.getLayoutParams().height = Utiles.dip2px(getContext(),heightDp);

         /* ------测试数据--------*/
        /*itemModels = DataEngine.getTestHeaderData(HEADER_SIZE, mHomeTab);
        mHeaderItem.addAll(itemModels);*/
    }

    @Override
    public void requestFocus() {
        mPlayHeaderRv.requestFocus();
    }

    @Override
    public void updateRecommendUI(List<ItemInfoBean> itemInfoBeans) {
        mHeaderItem.clear();
        //如果没有数据 暂时使用默认数据
        if(CollectionUtils.isEmpty(itemInfoBeans)){
            itemInfoBeans = DataEngine.getItemInfoFromLocal(activity, "item_json.json").subList(0, HEADER_SIZE);
        }
        mHeaderItem.addAll(ConvertManager.getInstance().convertItemToHeader(itemInfoBeans, mHomeTab));
        mHeaderAdapter.notifyDataSetChanged();
    }

}
