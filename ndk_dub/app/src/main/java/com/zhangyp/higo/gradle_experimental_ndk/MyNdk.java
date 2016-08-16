package com.zhangyp.higo.gradle_experimental_ndk;

/**
 * Created by zhangyipeng on 16/2/28.
 */
public class MyNdk {

    static {
        System.loadLibrary("MyLibrary");
    }

    public native String getString();

}
