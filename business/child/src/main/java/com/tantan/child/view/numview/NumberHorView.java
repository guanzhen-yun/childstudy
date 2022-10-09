package com.tantan.child.view.numview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.tantan.child.view.numview.viewinterface.INumView;

/**
 * 水平数字部分
 */
public class NumberHorView extends View implements INumView {

  private Paint mPaint;

  public NumberHorView(Context context) {
    this(context, null);
  }

  public NumberHorView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NumberHorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    setMeasuredDimension(60, 30);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    RectF rectF = new RectF(0, 0, 60, 30);
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
