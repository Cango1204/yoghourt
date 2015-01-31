package com.example.hbjia.level2.appinfo;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/1/31.
 */
public class AppInfo {
    private int versionCode = 0; //版本号
    private String appName = "";
    private String packageName = "";
    private String versionName = "";
    private Drawable appIcon = null;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
