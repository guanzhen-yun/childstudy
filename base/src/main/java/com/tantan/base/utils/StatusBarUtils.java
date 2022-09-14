package com.tantan.base.utils;

import android.app.Activity;
import android.graphics.Color;
import com.ziroom.base.StatusBarUtil;

/**
 * 状态栏工具类
 */
public class StatusBarUtils {

  //设置沉浸式
  public static void setStatusBarImmence(Activity context) {
    StatusBarUtil.setStatusFrontColorDark(context);
    com.jaeger.library.StatusBarUtil.setColor(context, Color.parseColor("#ffffe9"), 0);
  }
}
