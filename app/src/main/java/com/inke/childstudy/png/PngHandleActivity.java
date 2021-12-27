package com.inke.childstudy.png;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.App.Png)
public class PngHandleActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;

    private Bitmap mBitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pnghandle;
    }

    @Override
    public void initViews() {
        showPng();
    }

    int i = 0;

    @OnClick({R.id.btn_copy, R.id.btn_reverse, R.id.btn_changepic, R.id.btn_mirror, R.id.btn_reback, R.id.btn_invertedimage, R.id.btn_color_handle
    ,R.id.btn_grey, R.id.btn_roundimg, R.id.btn_daoying, R.id.btn_water, R.id.btn_view, R.id.tv_back})
    public void OnClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_reback:
                showPng();
                break;
            case R.id.btn_copy:
                copyImage();
                break;
            case R.id.btn_reverse:
                i++;
                reverseImage(i);
                break;
            case R.id.btn_changepic:
                changeImage();
                break;
            case R.id.btn_mirror:
                mirrorImage();
                break;
            case R.id.btn_invertedimage:
                invertedImage();
                break;
            case R.id.btn_color_handle:
                colorHandle();
                break;
            case R.id.btn_grey:
                convertGreyImg();
                break;
            case R.id.btn_roundimg:
                convertRoundImg(30);
                break;
            case R.id.btn_daoying:
                createReflectionImageWithOrigin();
                break;
            case R.id.btn_water:
                createBitmap();
                break;
            case R.id.btn_view:
                viewToBitmap();
                break;
        }
    }

    /**
     * 把一个View的对象转换成bitmap
     */
    private void viewToBitmap() {
        TextView view = new TextView(this);
        view.setText("淼淼你好啊");
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        mIvPic.setImageBitmap(bitmap);
    }

    /**
     * create the bitmap from a byte array 生成水印图片
     *
     */
    private void createBitmap() {
        Bitmap src =  BitmapFactory.decodeFile(getImagePath()); //要添加水印的图片
        Bitmap watermark = BitmapFactory.decodeFile(getWaterPath()); //水印图片
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, 0, 0, null);// 在src的左上角画入水印
        // save all clip
        cv.save();// 保存
        // store
        cv.restore();// 存储
        mIvPic.setImageBitmap(newb);
    }

    /**
     * 获得带倒影的图片方法
     */
    public void createReflectionImageWithOrigin() {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        mIvPic.setImageBitmap(bitmapWithReflection);
    }

    /**
     * 圆角图
     */
    public void convertRoundImg(float roundPx) {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        mIvPic.setImageBitmap(output);
    }

    /**
     * 将彩色图转换为灰度图
     */
    public void convertGreyImg() {
        //加载原图
        Bitmap img = BitmapFactory.decodeFile(getImagePath());
        int width = img.getWidth(); //获取位图的宽
        int height = img.getHeight(); //获取位图的高
        int []pixels = new int[width * height]; //通过位图的大小创建像素点数组
        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int red = ((grey & 0x00FF0000 ) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        mIvPic.setImageBitmap(result);
    }

    /**
     * 颜色处理
     */

    private void colorHandle() {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        ColorMatrix cm = new ColorMatrix();
        cm.set(//默认颜色矩阵,通过修改rgba来对图片颜色进行处理
                new float[]{
                        4,0,0,0,0,
                        0,2,0,0,0,
                        0,0,2,0,0,
                        0,0,0,5,0,
                }
        );
        /*
        颜色矩阵计算公式:
        red   = 1*128 + 0*128 + 0*128 + 0*0 +0
        blue  = 0*128 + 1*128 + 0*128 + 0*0 +0
        green = 0*128 + 0*128 + 1*128 + 0*0 +0
        alpha = 0*128 + 0*128 + 0*128 + 1*0 +0  透明度
        */
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bitmap,new Matrix(), paint);
        mIvPic.setImageBitmap(copybm);
    }

    /**
     * 倒影
     */
    private void invertedImage() {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{//这是矩阵的默认值
                1,0,0,
                0,-1,0,
                0,0,1
        });
        //镜像完还要平移回来
        matrix.postTranslate(0, bitmap.getHeight());
        //向画布绘制,绘制原图内容
        canvas.drawBitmap(bitmap, matrix, paint);
        mIvPic.setImageBitmap(copybm);
    }

    /**
     * 镜像
     */
    private void mirrorImage() {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{//这是矩阵的默认值
                -1,0,0,
                0,1,0,
                0,0,1
        });
        //镜像完还要平移回来
        matrix.postTranslate(bitmap.getWidth(), 0);
        //向画布绘制,绘制原图内容
        canvas.drawBitmap(bitmap, matrix, paint);
        mIvPic.setImageBitmap(copybm);
    }

    /**
     * 改变图片大小和位置
     */
    private void changeImage() {
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        //设置图片绘制角度——设置矩阵
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{//这是矩阵的默认值
                2,0,0,
                0,3,0,
                0,0,4
        });
        /**
         位置矩阵计算公式(以默认值为例,计算x、y、z轴的值):
         x = 1x+0y+0z;
         y = 0x+1y+0z;
         z = 0x+0y+1z;
         通过改变矩阵值可以修改图片
         //图像的缩放也可以使用Android中自带的方法进行设置
         matrix.setScale(0.5f, 0.5f);
         */
        //向画布绘制,绘制原图内容
        canvas.drawBitmap(bitmap, matrix, paint);
        mIvPic.setImageBitmap(copybm);
    }

    private String getImagePath() {
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "mypng.jpg");
        return f.getAbsolutePath();
    }

    private String getWaterPath() {
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "water.jpg");
        return f.getAbsolutePath();
    }

    private String getBigImagePath() {
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "bigpng.jpg");
        return f.getAbsolutePath();
    }

    /**
     *     图片旋转:
     *Android中原图是不能进行操作的,必须要先复制一张图到内存,然后再操作
     *旋转是在绘制过程中进行的
     * */

    private void reverseImage(int i) {
        //加载原图
        if(mBitmap == null) {
            mBitmap = BitmapFactory.decodeFile(getImagePath());
        }
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
       //设置图片绘制角度——设置矩阵
        Matrix matrix = new Matrix();
        /**
         matrix.setValues(new float[]{//这是矩阵的默认值
         1.5f,0,0,
         0,1,0,
         0,0,1
         });
         而旋转其实是将每个点坐标和sinx  cosx进行计算...
         */
        //安卓提供了便捷方法
        matrix.setRotate(30 * i,copybm.getWidth()/2,copybm.getHeight()/2);
        //向画布绘制,绘制原图内容
        canvas.drawBitmap(mBitmap, matrix, paint);
        mIvPic.setImageBitmap(copybm);
    }

    /**
     *  复制图片:作用
     *  在Android中,直接从资源文件加载到的图片是不能进行操作的,只能进行显示
     *  想要进行操作,可以复制一张图片到内存,然后操作复制到的图片
     * */
    private void copyImage() {
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "mypng.jpg");
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        //搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //获取复制图的画布
        Canvas canvas = new Canvas(copybm);
        //获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setStrokeWidth(24);
        paint.setColor(Color.RED);
        paint.setTextSize(60);
        paint.setStyle(Paint.Style.FILL);
        //向画布绘制,绘制原图内容
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        canvas.drawText("复制的图片", 130, 130, paint);
        paint.setColor(Color.BLACK);
        canvas.drawPoint(200, 200, paint); //向指定位置画一个点
        mIvPic.setImageBitmap(copybm);
    }

    /**
     * 将图像加载到内存中
     * Android为此操作提供了多种输入源:
     * `BitmapFactory.decodeByteArray(data, offset, length);
     * BitmapFactory.decodeFile(pathName);
     * BitmapFactory.decodeFile(pathName, opts);
     * BitmapFactory.decodeStream(is);
     * 等方法,可以根据图片源所在的位置不同使用不同的方法进行加载
     */
    private void showPng() {
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "mypng.jpg");
        Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath());
        //将内存中的图像显示在ImageView控件上
        mIvPic.setImageBitmap(bm);
    }

    /**
     * 在Android中,每一个应用程序所占用的内存空间大小都会有一个固定的大小限制
     * 假设此处加载的图片是2560*1440像素,图片位深是24的jpg格式图像
     * 虽然此图占用的磁盘空间是1.3M,但图片在加载到内存中时,实际上会先转换成位图图像
     * 那么这张图片加载到内存中的大小就是2560*1440*32(位深24,windows系统中,使用24位字节表示一个颜色值:#000000,
     * 但在Android中,每一个颜色值是用32位字节表示一个颜色值:#00000000),因此,这张图片加载到内存中所需要占用的内存
     * 大小约为:14M,因此,占用内存是极大的.若是直接将图片加载到内存中,容易造成内存溢出
     * 解决方案:按比例压缩图片
     * 按比例压缩图片首先就是要获取图片的大小
     *
     */

    private void showBigPng() {
        //用于设置图片渲染器参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置图片加载属性:不加载图片内容,只获取图片信息
        options.inJustDecodeBounds = true;
        //加载图片信息
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "bigpng.jpg");
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        //获取图片宽高
        int picwidth = options.outWidth;
        int picheight = options.outHeight;
        //获取屏幕大小
        //获取窗口管理器
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //获取默认显示设备
        Display dis = wm.getDefaultDisplay();
        //获取屏幕宽高
        //dis.getSize(outSize);此方法适用于新版本Android系统
        int diswidth = dis.getWidth();
        int disheight = dis.getHeight();
        //计算压缩比
        int wr = picwidth / diswidth;
        int hr = picheight / disheight;
        int r = 1;
        if (wr > hr && wr > 1) {
            r = wr;
        }
        if (hr > wr && hr > 1) {
            r = hr;
        }
        //压缩图片
        options.inSampleSize = r;//设置压缩比
        options.inJustDecodeBounds = false;//设置加载图片内容
        Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        mIvPic.setImageBitmap(bm);
    }

    private Bitmap getBigPng() {
        //用于设置图片渲染器参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置图片加载属性:不加载图片内容,只获取图片信息
        options.inJustDecodeBounds = true;
        //加载图片信息
        String s = Environment.getExternalStorageDirectory().toString();
        File f = new File(s, "bigpng.jpg");
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        //获取图片宽高
        int picwidth = options.outWidth;
        int picheight = options.outHeight;
        //获取屏幕大小
        //获取窗口管理器
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //获取默认显示设备
        Display dis = wm.getDefaultDisplay();
        //获取屏幕宽高
        //dis.getSize(outSize);此方法适用于新版本Android系统
        int diswidth = dis.getWidth();
        int disheight = dis.getHeight();
        //计算压缩比
        int wr = picwidth / diswidth;
        int hr = picheight / disheight;
        int r = 1;
        if (wr > hr && wr > 1) {
            r = wr;
        }
        if (hr > wr && hr > 1) {
            r = hr;
        }
        //压缩图片
        options.inSampleSize = r;//设置压缩比
        options.inJustDecodeBounds = false;//设置加载图片内容
        Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        return bm;
    }
}
