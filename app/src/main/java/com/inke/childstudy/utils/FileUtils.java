package com.inke.childstudy.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.inke.childstudy.entity.event.DownPngSuccess;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    //上传蒲公英
//    curl -F "file=@/Users/guanzhen/Downloads/ChildStudy_1.0.1.apk" -F "uKey=b1c9019a43ad85d695217fbfa6d2e403" -F "_api_key=8ea0d7054a6a7ff94164bbcd975a372f" https://upload.pgyer.com/apiv1/app/upload
//    {"code":0,"message":"","data":{"appKey":"e78056f2e529ecf03972974369180707","userKey":"b1c9019a43ad85d695217fbfa6d2e403","appType":"2","appIsLastest":"1","appFileSize":"16842407","appName":"ChildStudy","appVersion":"1.0.1","appVersionNo":"2","appBuildVersion":"16","appIdentifier":"com.inke.childstudy","appIcon":"a0e4ee5464b095c5f3cd04d7bed8c169","appDescription":"","appUpdateDescription":"","appScreenshots":"","appShortcutUrl":"EPkO","appCreated":"2021-09-13 18:52:50","appUpdated":"2021-09-13 18:52:50","appQRCodeURL":"https:\/\/www.pgyer.com\/app\/qrcodeHistory\/f382210d608753af2f2653add293c59cb0e98c5fdbcb5a9f258caca684e9c232"}}%

    //http://www.pgyer.com/apiv1/app/install?_api_key=8ea0d7054a6a7ff94164bbcd975a372f&aKey=e78056f2e529ecf03972974369180707  下载地址
    private String SDPATH;

    public static FileUtils getInstance() {
        return new FileUtils();
    }

    public String getDownloadPath() {
        File dir = new File(SDPATH + "downloadapk/");
        dir.mkdirs();
        return dir.getPath();
    }

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        //得到当前外部存储设备的目录
        // /SDCARD
        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }

    /*** 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                DownPngSuccess downPngSuccess = new DownPngSuccess();
                downPngSuccess.mFile = file;
                EventBus.getDefault().post(downPngSuccess);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
