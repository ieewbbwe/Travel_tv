package com.wisesoft.traveltv.ui.view.weight.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_mobile.core.utiles.CollectionUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.FilterAdapter;
import com.wisesoft.traveltv.model.temp.InitDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by picher on 2017/9/24.
 * Describe：
 */

public class TVFilterView extends LinearLayout {

    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private List<FilterAdapter> mInflaterAdapters = new ArrayList<>();

    public TVFilterView(Context context) {
        this(context, null);
    }

    public TVFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
    }

    public View newFilterLayout(final InitDataBean filterBean, final int i) {
        View mFilterLayout = mInflater.inflate(R.layout.layout_filter_item, null);
        TextView mFilterTitleTv = (TextView) mFilterLayout.findViewById(R.id.m_filter_title_tv);
        TvRecyclerView mFilterRlv = (TvRecyclerView) mFilterLayout.findViewById(R.id.m_filter_rlv);
        mFilterTitleTv.setText(filterBean.getName());

        FilterAdapter mAdapter = new FilterAdapter(getContext());
        mAdapter.setParentFilter(filterBean);
        mAdapter.setDataList(filterBean.getChildBean());
        mFilterRlv.setLayoutManager(new V7LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false));
        mFilterRlv.setAdapter(mAdapter);

        mFilterRlv.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                super.onItemClick(parent, itemView, position);
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(itemView, filterBean,
                            (InitDataBean) itemView.getTag());
                    setSelect(i,position);
                }
            }
        });
        mInflaterAdapters.add(mAdapter);
        return mFilterLayout;

    }


    /**
     * 筛选控件设置数据
     *
     * @param parentFilter 父级筛选集合
     */
    public void setFilterList(List<InitDataBean> parentFilter) {
        if(CollectionUtils.isNotEmpty(parentFilter)){
            mInflaterAdapters.clear();
            View v;
            for (int i = 0; i < parentFilter.size(); i++) {
                v = newFilterLayout(parentFilter.get(i), i);
                addView(v);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置第几个为选中
     */
    public void setSelect(int parentPos, int childPos) {
        FilterAdapter adapter = mInflaterAdapters.get(parentPos);
        adapter.setItemSelected(childPos);
    }

   /* public List<InitDataBean> getFilterParam() {
        List<InitDataBean> filterParam = new ArrayList<>();
        for(FilterAdapter adapter:mInflaterAdapters){
            filterParam.add(new InitDataBean());
        }
        return null;
    }*/
}
