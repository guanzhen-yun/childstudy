package com.inke.childstudy.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * 埋点统计
 */
public class TrackUtils {
    public static void track(Context context, String trackKey, Map<String, Object> map) {
        MobclickAgent.onEventObject(context, trackKey, map);
    }
}