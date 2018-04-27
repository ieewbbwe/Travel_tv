package com.wisesoft.traveltv;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.android_mobile.core.manager.SharedPrefManager;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.helper.InitDataCacheManager;
import com.wisesoft.traveltv.ui.HomeActivity;
import com.wisesoft.traveltv.ui.change.HomeChangeActivity;
import com.wisesoft.traveltv.ui.newdesign.HomeNewDesignActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by mxh on 2017/8/8.
 * Describe：广告页面
 */

public class LoadingActivity extends NActivity {

    @Bind(R.id.m_loading_iv)
    ImageView mLoadingIv;
    private DataBaseDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mDao = new DataBaseDao(mContext);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //由于没有用户系统 根据当前时间生成一个MD5码唯一标识当前用户
                //加载一些初始化数据
                updateInitData();
            }
        }, 200);
    }

    private void updateInitData() {
        /*if (!SharedPrefManager.getBoolean(Constans.IS_INIT_DATA, false)) {
            Executors.newCachedThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    mDao.initDatabase();
                }
            });
        }*/
        new InitDataCacheManager(mDao).start();

        pushActivity(HomeNewDesignActivity.class, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
