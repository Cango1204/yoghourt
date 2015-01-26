package com.example.hbjia.level2.imageviewer;

/**
 * Created by Administrator on 2015/1/26.
 */
public class ImageLoader {

    private static ImageLoader mInstance;

    private ImageLoader(){

    }

    //懒加载的单例，获得该实例对象
    public static ImageLoader getInstance() {
        if(mInstance == null) {
            synchronized (ImageLoader.class) {
                if(mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

//    private ImageLoader(int threadCount, Type type) {
//
//    }
}
