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
public class ArrowLeftView extends View {

  private Paint mPaint;
  private Path mPath;

  public ArrowLeftView(Context context) {
    this(context, null);
  }

  public ArrowLeftView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ArrowLeftView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    mPath.moveTo(65, 15);
    mPath.lineTo(65, 55);
    mPath.lineTo(35, 55);
    mPath.lineTo(35, 65);
    mPath.lineTo(0, 35);
    mPath.lineTo(35, 0);
    mPath.lineTo(35, 10);
    mPath.lineTo(65, 10);
    mPath.close();
    canvas.drawPath(mPath, mPaint);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(65, 65);
  }
}
