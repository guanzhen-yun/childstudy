package com.inke.childstudy.filesave;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.HttpDownloader;
import com.ziroom.base.BaseActivity;

import butterknife.OnClick;

/**
 * 文件保存
 */
@Route(path = RouterConstants.App.FileSave)
public class FileSaveActivity extends BaseActivity {

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

                break;
            case R.id.tv_downloadpng:

                break;
        }
    }

    class DownloadListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {

            Thread thread = new Thread() {
                @Override
                public void run() {
                    HttpDownloader httpDownLoader = new HttpDownloader();
                    httpDownLoader.downfile("http://192.168.0.102:8080/data/data.txt", "data/", "data.txt");
                    //使用Toast时相当于修改了UI,程序报错
                    //                     if(result==0)
                    //                     {
                    //                         Toast.makeText(MainActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                    //                     }
                    //                     else if(result==1) {
                    //                         Toast.makeText(MainActivity.this, "已有文件！", Toast.LENGTH_SHORT).show();
                    //                   }
                    //                     else if(result==-1){
                    //                         Toast.makeText(MainActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();
                    //                     }
                }
            };
            thread.start();

        }

    }
}
