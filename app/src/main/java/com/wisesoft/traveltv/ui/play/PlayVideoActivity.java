package com.wisesoft.traveltv.ui.play;

import android.os.Bundle;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;
import com.wisesoft.traveltv.NActivity;
import com.wisesoft.traveltv.R;

public class PlayVideoActivity extends NActivity {


    private StandardGSYVideoPlayer mVideoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
    }

    @Override
    protected void initComp() {
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        mVideoV = (StandardGSYVideoPlayer) findViewById(R.id.m_video_gsy);
        mVideoV.setUp(url,false,"");
        mVideoV.startPlayLogic();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
