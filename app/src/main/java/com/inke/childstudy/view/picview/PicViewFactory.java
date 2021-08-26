package com.inke.childstudy.view.picview;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PicViewFactory {
    public static final int PIC_CF = 0;//长方形
    public static final int PIC_ZF = 1;//正方形
    public static final int PIC_PX = 2;//平行四边形
    public static final int PIC_LX = 3;//菱形
    public static final int PIC_SJ = 4;//三角形
    public static final int PIC_T = 5;//梯形
    public static final int PIC_CIRCLE = 6;//圆形
    public static final int PIC_TY = 7;//椭圆形

    private Map<Integer, String> mapView = new HashMap<>();

    private PicViewFactory(){}

    private volatile static PicViewFactory mFactory;

    public static PicViewFactory getInstance() {
        if(mFactory == null) {
            synchronized (PicViewFactory.class) {
                if(mFactory == null) {
                    mFactory = new PicViewFactory();
                }
            }
        }
        return mFactory;
    }

    public void initViewList() {
        mapView.put(PIC_CF, "长方形");
        mapView.put(PIC_ZF, "正方形");
        mapView.put(PIC_PX, "平行四边形");
        mapView.put(PIC_LX, "菱形");
        mapView.put(PIC_SJ, "三角形");
        mapView.put(PIC_T, "梯形");
        mapView.put(PIC_CIRCLE, "圆形");
        mapView.put(PIC_TY, "椭圆形");
    }

    public String getViewStr(int viewType) {
        return mapView.get(viewType);
    }

    public PicChildView setRandomView(Context context) {
        if(mapView.size() <= 0) {
            initViewList();
        }
        Random random = new Random();
        int picType = random.nextInt(mapView.size());
        return getPicView(context, picType);
    }

    private PicChildView getPicView(Context context, int picType) {
        switch (picType) {
            case PIC_CF:
                return new ChangFangView(context);
            case PIC_ZF:
                return new ZhengFangView(context);
            case PIC_PX:
                return new PingXingView(context);
            case PIC_LX:
                return new LingXingView(context);
            case PIC_SJ:
                return new SanJiaoView(context);
            case PIC_T:
                return new TiXingView(context);
            case PIC_CIRCLE:
                return new CircleView(context);
            case PIC_TY:
                return new TuoYuanView(context);
        }
        return null;
    }
}
