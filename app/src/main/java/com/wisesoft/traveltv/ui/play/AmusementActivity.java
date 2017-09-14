package com.wisesoft.traveltv.ui.play;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemListAdapter;
import com.wisesoft.traveltv.model.ItemInfoBean;
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
    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amusement);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder();
        mAdapter = new ItemListAdapter(this);
    }

    @Override
    protected void initListener() {
        mReturnTiv.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                pushActivity(AmusementDetailActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        /*----测试数据-----*/
        for (int i = 0; i < 10; i++) {
            beanList.add(new ItemInfoBean("三峡大坝" + i));
        }
        /*----测试数据----*/
        mAdapter.setDataList(beanList);
        mListRlv.setAdapter(mAdapter);
        mListRlv.setLayoutManager(new LinearLayoutManager(this));
        mListRlv.setSpacingWithMargins(10, 10);//设置行列间距
        mListRlv.setSelectedItemAtCentered(true);//条目居中

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_return_tiv:
                popActivity();
                break;
        }
    }
}
