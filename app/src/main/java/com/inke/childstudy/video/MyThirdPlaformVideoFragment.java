package com.inke.childstudy.video;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.inke.childstudy.R;
import com.ziroom.base.BaseFragment;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyThirdPlaformVideoFragment extends BaseFragment {

    @BindView(R.id.videoView)
    JCVideoPlayerStandard videoSuf;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mythirdvideo;
    }

    @Override
    public void initViews(View mView) {
        boolean setUp = videoSuf.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST, "网络视频");
        if (setUp) {
            videoSuf.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load("https://scpic.chinaz.net/files/pic/pic9/202109/apic35010.jpg").into(videoSuf.thumbImageView);
        }
    }
}
