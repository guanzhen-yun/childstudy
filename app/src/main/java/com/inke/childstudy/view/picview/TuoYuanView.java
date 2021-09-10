package com.inke.childstudy.view.picview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * 椭圆形
 */
public class TuoYuanView extends PicChildView {
    private Paint mPaint;
    private Paint mTextPaint;
    private String mViewStr;
    private int width;
    private int height;

    public TuoYuanView(Context context) {
        this(context, null);
    }

    public TuoYuanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TuoYuanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    public int getViewType() {
        return PicViewFactory.PIC_TY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(600, 390);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(4, 4, 596, 386);
        canvas.drawRoundRect(rectF, 150, 150, mPaint);
        canvas.drawText(mViewStr, 600.0f / 2.0f - 4.0f, (390.0f + height) / 2.0f - 4, mTextPaint);
    }
}
