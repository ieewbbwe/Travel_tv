package com.wisesoft.traveltv.ui.newdesign.page;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.net.response.BaseResponse;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemLinearAdapter;
import com.wisesoft.traveltv.adapter.KeyBoardAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.KeyBoardItemBean;
import com.wisesoft.traveltv.model.temp.DataEngine;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;
import com.wisesoft.traveltv.net.ApiFactory;
import com.wisesoft.traveltv.net.OnSimpleCallBack;
import com.wisesoft.traveltv.ui.ProjectDetailActivity;
import com.wisesoft.traveltv.ui.newdesign.BaseNewDesignFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by picher on 2018/4/1.
 * Describe：
 */

public class SearchFragment extends BaseNewDesignFragment implements View.OnClickListener {

    @Bind(R.id.m_input_et)
    EditText mInputEt;
    /* @Bind(R.id.m_keyboard_kb)
     SkbContainer mKeyboardKb;*/
    @Bind(R.id.m_keyboard_rv)
    TvRecyclerView mKeyBoardRv;
    @Bind(R.id.m_result_trv)
    TvRecyclerView mResultTrv;
    @Bind(R.id.m_tip_tv)
    TextView mTipTv;
    @Bind(R.id.m_empty_container)
    RelativeLayout mEmptyContainer;
    @Bind(R.id.m_confirm_bt)
    Button mConfirmBt;
    @Bind(R.id.m_clear_bt)
    Button mClearBt;

    private ItemLinearAdapter mAdapter;
    private List<ItemInfoBean> mResultList = new ArrayList<>();
    private List<KeyBoardItemBean> mKeyBoardBeans = new ArrayList<>();
    private int limit = 10;
    private int page = 1;
    private String mKey;
    private KeyBoardAdapter mKeyBoardAdapter;
    private V7GridLayoutManager mKeyBordManagement;

    @Override
    protected int create() {
        return R.layout.fragment_fast_search;
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this, v);
        //初始化键盘
        mKeyBoardAdapter = new KeyBoardAdapter(getActivity());
        mKeyBordManagement = new V7GridLayoutManager(getActivity(), 5);
        mKeyBoardRv.setLayoutManager(mKeyBordManagement);
        mKeyBoardRv.setAdapter(mKeyBoardAdapter);
        if (CollectionUtils.isEmpty(mKeyBoardBeans)) {
            mKeyBoardBeans.addAll(DataEngine.getKeyBrodData());
        }
        mKeyBoardAdapter.setDataList(mKeyBoardBeans);

        //初始化结果列表
        mAdapter = new ItemLinearAdapter(getActivity());
        mResultTrv.setLayoutManager(new V7LinearLayoutManager(getActivity()));
        mResultTrv.setSelectedItemAtCentered(true);
        mResultTrv.setAdapter(mAdapter);

        mEmptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
       /* //在输入暂停1000ms后去查询数据
        RxTextView.textChanges(mInputEt)
                .debounce(1000, TimeUnit.MILLISECONDS)//设置时间
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                page = 1;
                mKey = charSequence.toString();
                mResultList.clear();
                mEmptyContainer.setVisibility(View.VISIBLE);
                mResultTrv.setVisibility(View.GONE);
                mTipTv.setText("正在查找中，请您稍后");
                requestProduct(mKey);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });*/

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, mAdapter.getDataList().get(position));
                pushActivity(intent, false);
            }
        });

        mResultTrv.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                if (hasMore()) {
                    page++;
                    requestProduct(mKey);
                    return true;
                }
                return false;
            }
        });
        mKeyBoardRv.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                mInputEt.setText(mInputEt.getText().toString() + mKeyBoardBeans.get(position).getName());
            }
        });

        mKeyBoardRv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    getFocusBorder().setVisible(false);
                }
            }
        });

        mConfirmBt.setOnClickListener(this);
        mClearBt.setOnClickListener(this);
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if (null != getFocusBorder()) {
            getFocusBorder().onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    protected void initData() {

    }

    private boolean hasMore() {
        return mResultList.size() >= limit * page;
    }

    /**
     * 根据拼音查询结果集
     *
     * @param py 拼音
     */
    private void requestProduct(String py) {
        Lg.d("picher", "searchKey：" + py);
      /*  if (CollectionUtils.isEmpty(mResultList)) {
            mResultList = mDataBaseDao.getItemInfos(20);
        }
        Collections.shuffle(mResultList);
        mAdapter.setDataList(mResultList);
        mEmptyV.setVisibility(View.GONE);*/

        ApiFactory.getTravelApi().getSearchList(py, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Response<BaseResponse<List<ItemInfoBean>>>>bindToLifecycle())
                .subscribe(new OnSimpleCallBack<Response<BaseResponse<List<ItemInfoBean>>>>() {
                    @Override
                    public void onResponse(Response<BaseResponse<List<ItemInfoBean>>> response) {
                        updateUI(response.body().getResponse());
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        if (page > 1) {
                            page--;
                        }
                    }
                });
    }

    private void updateUI(List<ItemInfoBean> response) {
        if (CollectionUtils.isNotEmpty(response)) {
            mResultTrv.setVisibility(View.VISIBLE);
            mResultList.addAll(response);
            mAdapter.setDataList(mResultList);
            mResultTrv.setAdapter(mAdapter);
            mEmptyContainer.setVisibility(View.GONE);
        } else {
            mTipTv.setText("没有找到数据，换一个关键词吧！");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void requestFocus() {
        mKeyBoardRv.requestFocus();
    }

    public void requestByKey(String key) {
        if (mInputEt != null) {
            page = 1;
            mResultList.clear();
            mEmptyContainer.setVisibility(View.VISIBLE);
            mResultTrv.setVisibility(View.GONE);
            mTipTv.setText("正在查找中，请您稍后...");
            requestProduct(mKey);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_confirm_bt:
                mKey = mInputEt.getText().toString();
                if (!TextUtils.isEmpty(mKey)) {
                    requestByKey(mKey);
                } else {
                    toast("请输入要搜索的资源缩写！");
                }
                break;
            case R.id.m_clear_bt:
                if (mInputEt != null) {
                    String text = mInputEt.getText().toString();
                    if (text.length() > 0) {
                        mInputEt.setText(text.substring(0, text.length() - 1));
                    }
                    //mKeyboardKb.onSoftKeyDown(KeyEvent.KEYCODE_DEL,null);
                }
                break;
        }
    }
}
