package com.example.hbjia.level2.windowmanager;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/1/31.
 */
public class FloatApplication extends Application{
    private WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }
}
