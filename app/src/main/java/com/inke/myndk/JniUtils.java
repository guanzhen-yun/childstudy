package com.inke.myndk;

public class JniUtils {
    public native String getString();

    static {
        System.loadLibrary("JniLibName"); //和生成so文件的名字对应。
    }
}
