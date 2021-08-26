package com.inke.childstudy.view.numview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class NumberVerView extends View implements INumView {
    private Paint mPaint;

    public NumberVerView(Context context) {
        this(context, null);
    }

    public NumberVerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberVerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#eeeeee"));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(30, 150);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, 30, 150);
        canvas.drawRoundRect(rectF, 10, 10, mPaint);
    }

    @Override
    public void setColorNum(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    public void setNormal() {
        mPaint.setColor(Color.parseColor("#eeeeee"));
        invalidate();
    }
}
