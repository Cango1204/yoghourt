package com.example.hbjia.level2.appinfo;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hbjia.level2.R;

import java.util.ArrayList;
import java.util.List;

public class AppInfoActivity extends Activity {

    private static final String TAG = "AppInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < getAppList().size(); i++) {
                    AppInfo appInfo = getAppList().get(i);
                    Log.e(TAG, appInfo.getAppName() + ", " + appInfo.getPackageName() + ", " +
                            appInfo.getVersionName() + ", " + appInfo.getVersionCode() + ", " +
                            appInfo.getAppIcon());
                }
            }
        }).start();
    }

    private ArrayList<AppInfo> getAppList() {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for(int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            appList.add(appInfo);
        }
        return appList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
