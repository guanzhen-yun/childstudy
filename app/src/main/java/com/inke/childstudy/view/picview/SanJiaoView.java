package com.inke.childstudy.view.picview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * 三角形
 */
public class SanJiaoView extends PicChildView {
    private Paint mPaint;
    private Paint mTextPaint;
    private Path mPath;
    private String mViewStr;
    private int width;
    private int height;

    public SanJiaoView(Context context) {
        this(context, null);
    }

    public SanJiaoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SanJiaoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setAntiAlias(true);//抗锯齿

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(60);
        mTextPaint.setAntiAlias(true);//抗锯齿
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.parseColor("#FF6347"));

        mViewStr = PicViewFactory.getInstance().getViewStr(getViewType());
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mViewStr, 0, mViewStr.length(), rect);
        width = rect.right - rect.left;
        height = rect.bottom - rect.top;

        mPath = new Path();
    }

    @Override
    public int getViewType() {
        return PicViewFactory.PIC_SJ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(198f, 4f);
        mPath.lineTo(4f, 396f);
        mPath.lineTo(396f, 396f);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.drawText(mViewStr, 400f / 2.0f - 4.0f, (400 + height) / 2.0f - 4, mTextPaint);
    }
}
