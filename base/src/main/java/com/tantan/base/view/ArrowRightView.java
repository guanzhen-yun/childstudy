package com.tantan.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * 自定义箭头
 */
public class ArrowRightView extends View {

  private Paint mPaint;
  private Path mPath;

  public ArrowRightView(Context context) {
    this(context, null);
  }

  public ArrowRightView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ArrowRightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setColor(Color.BLACK);
    mPaint.setStyle(Style.FILL);
    mPaint.setStrokeWidth(1);
    mPath = new Path();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.moveTo(0, 15);
    mPath.lineTo(30, 15);
    mPath.lineTo(30, 0);
    mPath.lineTo(65, 35);
    mPath.lineTo(30, 65);
    mPath.lineTo(30, 55);
    mPath.lineTo(0, 55);
    mPath.close();
    canvas.drawPath(mPath, mPaint);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(65, 65);
  }
}
