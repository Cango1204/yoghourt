package com.example.hbjia.level2.crash;

import android.app.Application;

/**
 * Created by Administrator on 2015/1/31.
 */
public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
