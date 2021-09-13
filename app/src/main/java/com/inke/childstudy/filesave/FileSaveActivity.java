package com.inke.childstudy.filesave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.event.DownPngSuccess;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.AppUtils;
import com.inke.childstudy.utils.DownloadUtils;
import com.inke.childstudy.utils.FileUtils;
import com.inke.childstudy.utils.HttpDownloader;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.view.dialog.DownloadCircleDialog;
import com.ziroom.base.BaseActivity;
import com.ziroom.net.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * 文件保存
 */
@Route(path = RouterConstants.App.FileSave)
public class FileSaveActivity extends BaseActivity {
    DownloadCircleDialog dialogProgress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_filesave;
    }

    @OnClick({R.id.tv_back, R.id.tv_downloadapk, R.id.tv_downloadpng})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_downloadapk:
                String down_url = "http://www.pgyer.com/apiv1/app/install?_api_key=8ea0d7054a6a7ff94164bbcd975a372f&aKey=e78056f2e529ecf03972974369180707";
                downloadApk(FileSaveActivity.this, down_url);
                break;
            case R.id.tv_downloadpng:
                v.setOnClickListener(new DownloadListener());
                break;
        }
    }

    @Override
    public void initViews() {
        dialogProgress = new DownloadCircleDialog(this);
    }

    //2.开始下载apk
    public void downloadApk(final Activity context, String down_url) {
        dialogProgress.show();
        DownloadUtils.getInstance().download(down_url, FileUtils.getInstance().getDownloadPath(), "childstudy.apk", new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                dialogProgress.dismiss();
                LogUtils.i("恭喜你下载成功，开始安装！==" + FileUtils.getInstance().getDownloadPath() + "childstudy.apk");
                ToastUtils.showToast("恭喜你下载成功，开始安装！");
                String successDownloadApkPath = FileUtils.getInstance().getDownloadPath() + "childstudy.apk";
                installApkO(FileSaveActivity.this, successDownloadApkPath);
            }

            @Override
            public void onDownloading(ProgressInfo progressInfo) {
                dialogProgress.setProgress(progressInfo.getPercent());
                boolean finish = progressInfo.isFinish();
                if (!finish) {
                    long speed = progressInfo.getSpeed();
                    dialogProgress.setMsg("(" + (speed > 0 ? speed / 1024 : speed) + "kb/s)正在下载...");
                } else {
                    dialogProgress.setMsg("下载完成！");
                }
            }

            @Override
            public void onDownloadFailed() {
                dialogProgress.dismiss();
                ToastUtils.showToast("下载失败！");
            }
        });

    }

    // 3.下载成功，开始安装,兼容8.0安装位置来源的权限
    private void installApkO(Context context, String downloadApkPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                LogUtils.i("8.0手机已经拥有安装未知来源应用的权限，直接安装！");
                AppUtils.installApk(context, downloadApkPath);
            } else {
                ToastUtils.showToast("安装应用需要打开安装未知来源应用权限，请去设置中开启权限");
                Uri packageUri = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                startActivityForResult(intent, 10086);
            }
        } else {
            AppUtils.installApk(context, downloadApkPath);
        }
    }

    //4.开启了安装未知来源应用权限后，再次进行步骤3的安装。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            LogUtils.i("设置了安装未知应用后的回调。。。");
            String successDownloadApkPath = FileUtils.getInstance().getDownloadPath() + "childstudy.apk";
            installApkO(FileSaveActivity.this, successDownloadApkPath);
        }
    }

    class DownloadListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    HttpDownloader httpDownLoader = new HttpDownloader();
                    httpDownLoader.downfile("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm2.biz.itc.cn%2Fpic%2Fnew%2Fn%2F57%2F19%2FImg7121957_n.jpg&refer=http%3A%2F%2Fm2.biz.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1634115200&t=a2bc232ceb61777879de9cfb2f3a07f6", "goodimg/", "girl.jpg");
                }
            };
            thread.start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downPngSuccess(DownPngSuccess success) {
        ToastUtils.showToast("下载图片成功");
        Uri uri = Uri.fromFile(success.mFile);                                  //获得图片的uri
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //发送广播通知更新图库，这样系统图库可以找到这张图片
    }

    @Override
    public boolean registEventBus() {
        return true;
    }
}
