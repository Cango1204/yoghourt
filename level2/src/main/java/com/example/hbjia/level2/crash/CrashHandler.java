package com.example.hbjia.level2.crash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/31.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler; //系统默认的UncaughtException处理类
    private static CrashHandler mInstance = new CrashHandler();
    private Context mContext;
    private Map<String, String> mInfo = new HashMap<String, String>();
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if(!handleException(throwable) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public boolean handleException(Throwable ex) {
        if(ex == null) {
            return false;
        }
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_SHORT).show();
//                new AlertDialog.Builder(mContext).setTitle("提示").setCancelable(false)
//                        .setMessage("程序崩溃了...").setNeutralButton("我知道了", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        System.exit(0);
//                    }
//
//                }).create().show();

                Looper.loop();
            }
        }.start();
        collectDeviceInfo();
        saveCrashInfo2File(ex);
        return true;
    }

    public void collectDeviceInfo() {
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if(packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                mInfo.put("versionName", versionName);
                mInfo.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();
        for(Field field : fields) {
            try {
                field.setAccessible(true);
                mInfo.put(field.getName(), field.get("").toString());
                Log.d(TAG, field.getName() + " : " + field.get(""));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> entry : mInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        while(cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();
        String result = writer.toString();
        sb.append(result);

        long timestamp = System.currentTimeMillis();
        String time = mFormat.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
            || !Environment.isExternalStorageRemovable()) {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "crash");
            Log.i(TAG, dir.toString());
            if(!dir.exists()) {
                dir.mkdir();
            }
            try {
                FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
