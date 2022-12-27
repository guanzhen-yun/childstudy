package com.tantan.userinfo.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import java.io.IOException;

/**
 * 相册工具类
 */
public class PhotoUtils {

  @SuppressLint("ObsoleteSdkInt")
  public static String getAlbumPath(Context context, Intent data) {
    if (Build.VERSION.SDK_INT >= 19) {
      // 4.4及以上系统使用这个方法处理图片
      return handleImageOnKitKat(context, data);
    } else {
      // 4.4以下系统使用这个方法处理图片
      return handleImageBeforeKitKat(context, data);
    }
  }

  private static String handleImageOnKitKat(Context context, Intent data) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(context, uri)) {
      // 如果是document类型的Uri，则通过document id处理
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1];
        // 解析出数字格式的id
        String selection = MediaStore.Images.Media._ID + "=" + id;
        imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri = ContentUris.withAppendedId(
            Uri.parse("content: //downloads/public_downloads"), Long.parseLong(docId));
        imagePath = getImagePath(context, contentUri, null);
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
      // 如果是content类型的Uri，则使用普通方式处理
      imagePath = getImagePath(context, uri, null);
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
      // 如果是file类型的Uri，直接获取图片路径即可
      imagePath = uri.getPath();
    }
    return imagePath;
  }

  @SuppressLint("Range")
  private static String getImagePath(Context context, Uri uri, String selection) {
    String path = null;
    // 通过Uri和selection来获取真实的图片路径
    Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
      }
      cursor.close();
    }
    return path;
  }

  /**
   * android 4.4以前的处理方式
   */
  private static String handleImageBeforeKitKat(Context context, Intent data) {
    Uri uri = data.getData();
    return getImagePath(context, uri, null);
  }

  /**
   * 加载旋转后的图片
   */
  public static Bitmap loadCompletePic(String filePath) {
    Bitmap newBitmap = null;
    if (filePath != null) {
      newBitmap = BitmapFactory.decodeFile(filePath);// 根据Path读取资源图片
      int angle = readPictureDegree(filePath);
      if (angle != 0) {
        // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
        Matrix m = new Matrix();
        int width = newBitmap.getWidth();
        int height = newBitmap.getHeight();
        m.setRotate(angle); // 旋转angle度
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, width, height, m,
            true);// 从新生成图片
        return newBitmap;
      }
    }
    return newBitmap;
  }

  /**
   * 读取图片属性：旋转的角度 图片绝对路径 degree旋转的角度
   */
  private static int readPictureDegree(String filePath) {
    int angle = 0;
    try {
      ExifInterface exifInterface = new ExifInterface(filePath);
      int orientation = exifInterface.getAttributeInt(
          ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_NORMAL);

      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          angle = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          angle = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          angle = 270;
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return angle;
  }
}
