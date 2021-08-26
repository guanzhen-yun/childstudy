package com.inke.childstudy.view.numview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.inke.childstudy.utils.NumUtils;

public class NumberParentView extends ViewGroup {
    private int mPaddingSmall = 3;
    private int mPaddingBig = 25;

    int horWidth = 0;
    int horHeight = 0;

    int verWidth = 0;
    int verHeight = 0;

    int[][] attr = new int[5][];

    public NumberParentView(Context context) {
        this(context, null);
    }

    public NumberParentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //顶部四个
        for (int i = 0; i < 4; i++) {
            addView(new NumberHorView(context));
        }
        //第二排四个
        for (int i = 0; i < 4; i++) {
            addView(new NumberVerView(context));
        }
        //第三排四个
        for (int i = 0; i < 4; i++) {
            addView(new NumberHorView(context));
        }
        //第四排四个
        for (int i = 0; i < 4; i++) {
            addView(new NumberVerView(context));
        }
        //第五排四个
        for (int i = 0; i < 4; i++) {
            addView(new NumberHorView(context));
        }

        for (int i = 0; i < 5; i++) {
            attr[i] = new int[]{4 * i, 4 * i + 1, 4 * i + 2, 4 * i + 3};
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(0, 0);
        }
        horWidth = getChildAt(0).getMeasuredWidth();
        horHeight = getChildAt(0).getMeasuredHeight();

        verWidth = getChildAt(4).getMeasuredWidth();
        verHeight = getChildAt(4).getMeasuredHeight();

        int width = horWidth * 4 + mPaddingSmall * 2 + mPaddingBig + 4 * verWidth;
        int height = verHeight * 2 + horHeight;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        int right = 0;
        int bottom = 0;
        int bottomPre = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (i < 4) { //第一排
                if (i == 0) {
                    left = verWidth;
                } else if (i == 1) {
                    left = right + mPaddingSmall;
                } else if (i == 2) {
                    left = right + verWidth * 2 + mPaddingBig;
                } else {
                    left = right + mPaddingSmall;
                }
                right = left + horWidth;
                childAt.layout(left, 0, right, horHeight);
            } else if (i < 8) { //第二排
                if (i == 4) {
                    left = 0;
                } else if (i == 5) {
                    left = right + horWidth * 2 + mPaddingSmall;
                } else if (i == 6) {
                    left = right + mPaddingBig;
                } else {
                    left = right + horWidth * 2 + mPaddingSmall;
                }
                right = left + verWidth;
                childAt.layout(left, horHeight / 2, right, verHeight + horHeight / 2);
                bottom = verHeight + horHeight / 2;
            } else if (i < 12) { //第三排
                bottomPre = bottom;
                if (i == 8) {
                    left = verWidth;
                } else if (i == 9) {
                    left = right + mPaddingSmall;
                } else if (i == 10) {
                    left = right + verWidth * 2 + mPaddingBig;
                } else {
                    left = right + mPaddingSmall;
                }
                right = left + horWidth;
                childAt.layout(left, bottomPre - horHeight / 2, right, bottomPre + horHeight / 2);
            } else if (i < 16) { //第四排
                bottomPre = bottom;
                if (i == 12) {
                    left = 0;
                } else if (i == 13) {
                    left = right + horWidth * 2 + mPaddingSmall;
                } else if (i == 14) {
                    left = right + mPaddingBig;
                } else {
                    left = right + horWidth * 2 + mPaddingSmall;
                }
                right = left + verWidth;
                childAt.layout(left, bottomPre, right, bottomPre + verHeight);
                if (i == 15) {
                    bottom = bottom + verHeight;
                }
            } else if (i < 20) { //第五排
                if (i == 16) {
                    left = verWidth;
                } else if (i == 17) {
                    left = right + mPaddingSmall;
                } else if (i == 18) {
                    left = right + verWidth * 2 + mPaddingBig;
                } else {
                    left = right + mPaddingSmall;
                }
                right = left + horWidth;
                childAt.layout(left, bottom - horHeight / 2, right, bottom + horHeight / 2);
            }
        }

    }

    /**
     * 初始化0
     */
    public void init() {
        setNum(0);
    }

    public void setNum(int num) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            INumView childAt = (INumView) getChildAt(i);
            childAt.setNormal();
        }
        int[] initAttr = NumUtils.getNumAttr(num, attr);
        for (int i = 0; i < childCount; i++) {
            INumView childAt = (INumView) getChildAt(i);
            for (int attr : initAttr) {
                if (i == attr) {
                    if(num <= 10) {
                        childAt.setColorNum(Color.parseColor("#000000"));
                    } else if(num <= 50){
                        childAt.setColorNum(Color.parseColor("#FF" + num + "" + num));
                    } else {
                        childAt.setColorNum(Color.parseColor("#FF" + num + "00"));
                    }
                }
            }
        }
    }
}
