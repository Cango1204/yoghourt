package com.example.hbjia.level2;

import android.app.Application;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

/**
 * Created by hbjia on 2015/1/29.
 */
public class UIApplication extends Application{

    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        forceShowOverflowMenu();
        super.onCreate();
    }
}
