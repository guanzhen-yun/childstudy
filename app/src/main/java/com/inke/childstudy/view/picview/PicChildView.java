package com.inke.childstudy.view.picview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 图形类接口
 */
public abstract class PicChildView extends View {


    public PicChildView(Context context) {
        super(context);
    }

    public PicChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PicChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    abstract int getViewType();


}
