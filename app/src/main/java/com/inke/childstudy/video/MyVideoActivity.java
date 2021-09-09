package com.inke.childstudy.video;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.ClickEventUtils;
import com.inke.childstudy.utils.DateUtils;
import com.ziroom.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.App.Video)
public class MyVideoActivity extends BaseActivity implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnVideoSizeChangedListener,
        SeekBar.OnSeekBarChangeListener {
    //1、MediaController+VideoView实现方式

    //2.MediaPlayer+SurfaceView+自定义控制器

    //3、MediaPlayer+SurfaceView+MediaController

    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.playOrPause)
    ImageView playOrPauseIv;
    @BindView(R.id.surfaceView)
    SurfaceView videoSuf;
    @BindView(R.id.tv_progess)
    SeekBar mSeekBar;
    @BindView(R.id.root_rl)
    RelativeLayout rootViewRl;
    @BindView(R.id.control_ll)
    LinearLayout controlLl;
    @BindView(R.id.tv_start_time)
    TextView startTime;
    @BindView(R.id.tv_end_time)
    TextView endTime;
    @BindView(R.id.iv_forward)
    ImageView forwardButton;
    @BindView(R.id.iv_backward)
    ImageView backwardButton;

    private MediaPlayer mPlayer;
    private String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private boolean isShow = false;
    public static final int UPDATE_TIME = 0x0001;
    public static final int HIDE_CONTROL = 0x0002;

    MediaController localMediaController;

    private MyVideoFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myvideo;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        fragment.show();
//        return super.onTouchEvent(event);
//    }

    @Override
    public void initViews() {
//        initNetVideo();
//        initLocalVideo();

//        mSeekBar.setOnSeekBarChangeListener(this);
//        videoSuf.setZOrderOnTop(false);
//        videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        videoSuf.getHolder().addCallback(this);
//        initPlayer();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        fragment = new MyVideoFragment();
//        transaction.replace(R.id.fl_body, fragment).commitAllowingStateLoss();
        transaction.replace(R.id.fl_body, new MyThirdPlaformVideoFragment()).commitAllowingStateLoss();
    }

    private void initPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnSeekCompleteListener(this);
        mPlayer.setOnVideoSizeChangedListener(this);
        try {
            String uri = ("android.resource://" + getPackageName() + "/" + R.raw.v1);
            mPlayer.setDataSource(MyVideoActivity.this, Uri.parse(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    updateTime();
                    mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
                    break;
                case HIDE_CONTROL:
                    hideControl();
                    break;
            }
        }
    };

    //播放本地视频
    private void initLocalVideo() {
        //设置有进度条可以拖动快进
        localMediaController = new MediaController(this);
        mVideoView.setMediaController(localMediaController);
        String uri = ("android.resource://" + getPackageName() + "/" + R.raw.v1);
        mVideoView.setVideoURI(Uri.parse(uri));
        //让VideoView获取焦点
        mVideoView.requestFocus();
        mVideoView.start();
    }

    //播放网络视频
    private void initNetVideo() {
        //设置有进度条可以拖动快进
        localMediaController = new MediaController(this);
        mVideoView.setMediaController(localMediaController);
        mVideoView.setVideoURI(Uri.parse(videoUrl));
        //让VideoView获取焦点
        mVideoView.requestFocus();
        mVideoView.start();
    }

    @OnClick({R.id.tv_back, R.id.playOrPause, R.id.root_rl, R.id.iv_forward, R.id.iv_backward})
    public void onClickView(View v) {
        if (ClickEventUtils.isFastDoubleClick(v)) return;
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.playOrPause:
                play();
                break;
            case R.id.root_rl:
                showControl();
                break;
            case R.id.iv_forward:
                forWard();
                break;
            case R.id.iv_backward:
                backWard();
                break;
        }
    }

    /**
     * VideoView内部的AudioManager会对Activity持有一个强引用，而AudioManager的生命周期比较长，导致这个Activity始终无法被回收。
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);

                return super.getSystemService(name);
            }
        });
    }

    private void play() {
        if (mPlayer == null) {
            return;
        }
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            mHandler.removeMessages(UPDATE_TIME);
            mHandler.removeMessages(HIDE_CONTROL);
            playOrPauseIv.setVisibility(View.VISIBLE);
            playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
        } else {
            mPlayer.start();
            mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
            mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
            playOrPauseIv.setVisibility(View.INVISIBLE);
            playOrPauseIv.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    /**
     * 更新播放时间
     */
    private void updateTime() {
        startTime.setText(DateUtils.timeToStr(
                mPlayer.getCurrentPosition()));
        mSeekBar.setProgress(mPlayer.getCurrentPosition());
    }

    /**
     * 隐藏进度条
     */
    private void hideControl() {
        isShow = false;
        mHandler.removeMessages(UPDATE_TIME);
        controlLl.animate().setDuration(300).translationY(controlLl.getHeight());
    }

    /**
     * 显示进度条
     */
    private void showControl() {
        if (isShow) {
            play();
        }
        isShow = true;
        mHandler.removeMessages(HIDE_CONTROL);
        mHandler.sendEmptyMessage(UPDATE_TIME);
        mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
        controlLl.animate().setDuration(300).translationY(0);
    }
    /**
     * 设置快进10秒方法
     */
    private void forWard(){
        if(mPlayer != null){
            int position = mPlayer.getCurrentPosition();
            mPlayer.seekTo(position + 10000);
        }
    }

    /**
     * 设置快退10秒的方法
     */
    public void backWard(){
        if(mPlayer != null){
            int position = mPlayer.getCurrentPosition();
            if(position > 10000){
                position-=10000;
            }else{
                position = 0;
            }
            mPlayer.seekTo(position);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        startTime.setText(DateUtils.timeToStr(mp.getCurrentPosition()));
        endTime.setText(DateUtils.timeToStr(mp.getDuration()));
        mSeekBar.setMax(mp.getDuration());
        mSeekBar.setProgress(mp.getCurrentPosition());
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
        mPlayer.prepareAsync();//prepare  资源小的
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if(mPlayer != null && b){
            mPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
