package com.inke.childstudy.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
