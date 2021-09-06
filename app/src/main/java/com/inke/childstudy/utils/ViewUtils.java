package com.inke.childstudy.utils;

import android.view.View;

public class ViewUtils {
    public static void setViewsGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setViewsVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
