package com.wisesoft.traveltv.ui.play;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemListAdapter;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.DisplayAdaptive;
import com.wisesoft.traveltv.ui.view.TVIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-玩
 */
public class AmusementActivity extends NActivity implements View.OnClickListener {

    @Bind(R.id.m_list_rlv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_play_tiv)
    TVIconView mPlayTiv;

    private List<ItemInfoBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amusement);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
    }

    @Override
    protected void initListener() {
        mReturnTiv.setOnClickListener(this);

        mListRlv.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {
                //mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0));
            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        /*----测试数据-----*/
        for(int i=0;i<10;i++){
            beanList.add(new ItemInfoBean("三峡大坝"+i));
        }
        /*----测试数据----*/
        ItemListAdapter mAdapter = new ItemListAdapter(this);
        mAdapter.setDataList(beanList);
        mListRlv.setAdapter(mAdapter);
        mListRlv.setLayoutManager(new LinearLayoutManager(this));
        mListRlv.setSpacingWithMargins(10, 10);//设置行列间距
        mListRlv.setSelectedItemAtCentered(true);//条目居中

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.m_return_tiv:
                popActivity();
                break;
        }
    }
}
