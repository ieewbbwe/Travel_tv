package com.wisesoft.traveltv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android_mobile.core.utiles.CollectionUtils;
import com.android_mobile.core.utiles.Lg;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7LinearLayoutManager;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ItemLinearAdapter;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.weight.keyboard.SkbContainer;
import com.wisesoft.traveltv.ui.view.weight.keyboard.SoftKey;
import com.wisesoft.traveltv.ui.view.weight.keyboard.SoftKeyBoardListener;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class SearchResultActivity extends NActivity {

    @Bind(R.id.m_result_trv)
    TvRecyclerView mResultTrv;
    @Bind(R.id.m_keyboard_kb)
    SkbContainer mKeyboardKb;
    @Bind(R.id.m_input_tv)
    EditText mInputEt;
    @Bind(R.id.m_empty_container)
    ViewGroup mEmptyV;

    private Object mOldSoftKey;
    private ItemLinearAdapter mAdapter;
    private List<ItemInfoBean> mResultList;
    private DataBaseDao mDataBaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }

    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        mKeyboardKb.setSkbLayout(R.xml.skb_all_key);
        mKeyboardKb.setFocusable(true);
        mKeyboardKb.setFocusableInTouchMode(true);
        setSkbContainerMove();

        mAdapter = new ItemLinearAdapter(this);
        mResultTrv.setLayoutManager(new V7LinearLayoutManager(this));
        mResultTrv.setSelectedItemAtCentered(true);
        mResultTrv.setAdapter(mAdapter);

        mEmptyV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        // 监听键盘事件.
        mKeyboardKb.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                int keyCode;
                String keyLabel = softKey.getKeyLabel();
                if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.
                    mInputEt.setText(mInputEt.getText() + softKey.getKeyLabel());
                } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
                    keyCode = softKey.getKeyCode();
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        String text = mInputEt.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            toast("已经没有内容了！");
                        } else {
                            mInputEt.setText(text.substring(0, text.length() - 1));
                        }
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                        finish();
                    } else if (keyCode == 66) {
                        Toast.makeText(getApplicationContext(), "回车", Toast.LENGTH_LONG).show();
                    } else if (keyCode == 250) { //切换键盘
                        // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
                           /* setSkbContainerOther();
                            mKeyboardKb.setSkbLayout(R.xml.sbd_number);*/
                    }
                }
            }

            @Override
            public void onBack(SoftKey key) {
                finish();
            }

            @Override
            public void onDelete(SoftKey key) {
                String text = mInputEt.getText().toString();
                if (text.length() > 0) {
                    mInputEt.setText(text.substring(0, text.length() - 1));
                }
            }

        });

        //在输入暂停1000ms后去查询数据
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
                requestProduct(charSequence.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchResultActivity.this, ProjectDetailActivity.class);
                intent.putExtra(Constans.ITEM_BEAN, mAdapter.getDataList().get(position));
                pushActivity(intent, false);
            }
        });
    }

    @Override
    protected void initData() {
        mDataBaseDao = new DataBaseDao(this);
    }

    private void setSkbContainerMove() {
        mOldSoftKey = null;
        mKeyboardKb.setMoveSoftKey(true); // 设置是否移动按键边框.
       /* RectF rectf = new RectF((int)getResources().getDimension(R.dimen.
                w_10), (int)getResources().getDimension(R.dimen.
                h_10), (int)getResources().getDimension(R.dimen.
                w_10), (int)getResources().getDimension(R.dimen.
                h_10));*/
        mKeyboardKb.setSoftKeySelectPadding(0); // 设置移动边框相差的间距.
        mKeyboardKb.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        mKeyboardKb.setSelectSofkKeyFront(false); // 设置选中边框在最前面.
    }

    /**
     * 根据拼音查询结果集
     *
     * @param py 拼音
     */
    private void requestProduct(String py) {
        Lg.d("picher", "searchKey：" + py);
        if (CollectionUtils.isEmpty(mResultList)) {
            mResultList = mDataBaseDao.getItemInfos(20);
        }
        Collections.shuffle(mResultList);
        mAdapter.setDataList(mResultList);
        mEmptyV.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mKeyboardKb.onSoftKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mKeyboardKb.onSoftKeyUp(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
