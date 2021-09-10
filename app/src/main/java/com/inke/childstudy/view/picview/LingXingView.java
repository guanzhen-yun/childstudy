package com.inke.childstudy.view.picview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * 菱形
 */
public class LingXingView extends PicChildView {
    private Paint mPaint;
    private Paint mTextPaint;
    private Path mPath;
    private String mViewStr;
    private int width;
    private int height;

    public LingXingView(Context context) {
        this(context, null);
    }

    public LingXingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LingXingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setAntiAlias(true);//抗锯齿
        // 为Paint设置渐变器
        Shader mShader = new LinearGradient(0, 0, 40, 60
                , new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW }
                , null , Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        //设置阴影
        mPaint.setShadowLayer(25 , 20 , 20 , Color.GRAY);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(90);
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
        return PicViewFactory.PIC_LX;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(450, 411);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(90f, 12f);
        mPath.lineTo(438f, 12f);
        mPath.lineTo(360f, 399f);
        mPath.lineTo(12f, 399f);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.drawText(mViewStr, 450f / 2.0f - 4.0f, (411 + height) / 2.0f - 4, mTextPaint);
    }
}
