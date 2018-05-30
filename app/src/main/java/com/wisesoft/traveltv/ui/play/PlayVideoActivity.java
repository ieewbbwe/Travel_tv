package com.wisesoft.traveltv.ui.play;

import android.os.Bundle;

import com.android_mobile.core.utiles.Lg;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

public class PlayVideoActivity extends NActivity {


    private StandardGSYVideoPlayer mVideoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
    }

    @Override
    protected void initComp() {
        //String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        mVideoV = (StandardGSYVideoPlayer) findViewById(R.id.m_video_gsy);
        Object obj = getIntent().getExtras().get(Constans.ITEM_BEAN);
        if (obj != null && obj instanceof ItemInfoBean) {
            //String mUrl = ((ItemInfoBean) obj).getFile_f();
            String mUrl = ((ItemInfoBean) obj).getVideo_path();//视频改为外站链接
            Lg.d("picher", "url+" + mUrl);
            mVideoV.setUp(mUrl, false, "");
            mVideoV.startPlayLogic();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
    }
}
