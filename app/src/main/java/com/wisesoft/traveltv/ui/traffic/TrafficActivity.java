package com.wisesoft.traveltv.ui.traffic;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_mobile.core.utiles.BitmapUtils;
import com.android_mobile.core.utiles.Lg;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.owen.tvrecyclerview.TwoWayLayoutManager;
import com.owen.tvrecyclerview.widget.GridLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.tv.boost.widget.focus.FocusBorder;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.TrafficAdapter;
import com.wisesoft.traveltv.model.DataEngine;
import com.wisesoft.traveltv.model.ItemInfoBean;
import com.wisesoft.traveltv.ui.view.TVIconView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mxh on 2017.09.05
 * Describe：分类-行
 */
public class TrafficActivity extends NActivity {

    @Bind(R.id.m_eat_tiv)
    TVIconView mEatTiv;
    @Bind(R.id.m_search_tiv)
    TVIconView mSearchTiv;
    @Bind(R.id.m_sort_tiv)
    TVIconView mSortTiv;
    @Bind(R.id.m_return_tiv)
    TVIconView mReturnTiv;
    @Bind(R.id.m_list_rlv)
    TvRecyclerView mListRlv;
    @Bind(R.id.m_title_tv)
    TextView mTitleTv;
    @Bind(R.id.m_introduce_tv)
    TextView mIntroduceTv;
    /*@Bind(R.id.m_second_list_rlv)
    TvRecyclerView mSecondListRlv;*/
    @Bind(R.id.m_content_fl)
    FrameLayout mBg;

    private List<ItemInfoBean> mDatas;
    private TrafficAdapter mAdapter;
    private List<ItemInfoBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
    }

    @Override
    protected void initComp() {
        initBorder();
        ButterKnife.bind(this);

        mList = DataEngine.getTrafficInfo();
        mAdapter = new TrafficAdapter(this);
        mAdapter.setDataList(mList);
        mListRlv.setAdapter(mAdapter);
        mListRlv.setSpacingWithMargins(15, 0);
        Lg.print("picher","交通页准备虚化");

        //虚化背景
        Glide.with(this).load("file:///android_asset/traffic_bg.png")
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Lg.print("picher","交通页开始处理虚化");
                        subscriber.onNext(BitmapUtils.blurImageAmeliorate(
                                BitmapUtils.ratio(resource,getScreenWidth(),getScreenHeight())
                        ));
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                Lg.print("picher","交通页虚化处理完成");
                                mBg.setBackground(new BitmapDrawable(bitmap));
                            }
                        });
            }
        });

    }

    @Override
    protected void initListener() {
        mListRlv.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {
            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                ItemInfoBean item =mAdapter.getItemObject(position);
                mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f, 1.1f, 4f));
                updateUI(item);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Lg.d("picher", "onItemClick：" + position);
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
    }

    private void updateUI(ItemInfoBean item) {
        mTitleTv.setText(item.getName());
        mIntroduceTv.setText(item.getIntroduceStr());
    }

    @Override
    protected void initData() {

    }
}
