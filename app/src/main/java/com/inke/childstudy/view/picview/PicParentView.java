package com.inke.childstudy.view.picview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.inke.childstudy.view.numview.NumberHorView;

public class PicParentView extends ViewGroup {
    private int mCurrentView = PicViewFactory.PIC_CF;
    private int measureWidth;
    private int measureHeight;
    private Context mContext;

    private int lastX;
    private int lastY;

    public PicParentView(Context context) {
        this(context, null);
    }

    public PicParentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        addView(PicViewFactory.getInstance().setRandomView(context));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(0, 0);
        }
        measureWidth = getChildAt(0).getMeasuredWidth();
        measureHeight = getChildAt(0).getMeasuredHeight();
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childAt = getChildAt(0);
        childAt.layout(0, 0, measureWidth, measureHeight);
    }

    public void setRandomView() {
        removeAllViews();
        init(mContext);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                lastX = 0;
                lastY = 0;
                break;
        }
        return true;

    }
}
