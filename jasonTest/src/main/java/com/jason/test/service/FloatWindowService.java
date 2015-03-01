/**
 * @author zhoushengtao
 * @since 2014年5月21日 下午6:18:29
 */

package com.jason.test.service;

import com.jason.test.JasonTestApplication;
import com.jason.test.handler.WeakRefHandler;
import com.jason.test.properties.Constants;
import com.jason.test.util.MemoryUtils;
import com.jason.test.util.StringUtils;
import com.jason.test.view.FloatView;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowService extends Service {
    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView mFloatView = null;
    private String mPkgName = "";
    private Timer mTimer;

    private WeakRefHandler mHandler = new WeakRefHandler(this) {
	@Override
	public void handleMessage(Message msg) {
	    long mem = msg.getData().getLong("memory");
	    Log.e("JASON_TEST", mPkgName + " Memory size = " + mem);
	    if (mem <= 0) {
		mFloatView.setMemInfo("");
		mFloatView.setPkgName("");
	    } else {
		mFloatView.setMemInfo(StringUtils.kbytes2FileSizeString(mem));
		mFloatView.setPkgName(mPkgName);
	    }

	    super.handleMessage(msg);
	}
    };

    @Override
    public IBinder onBind(Intent intent) {
	return null;
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	// 在程序退出(Activity销毁）时销毁悬浮窗口
	mWindowManager.removeView(mFloatView);
	mTimer.cancel();
	mTimer = null;
    }

    @Override
    public void onCreate() {
	super.onCreate();
	createView();
    }

    private void createView() {
	mFloatView = new FloatView(getApplicationContext());
	mWindowManager = (WindowManager) getApplicationContext().getSystemService(
		Context.WINDOW_SERVICE);
	windowManagerParams = ((JasonTestApplication) getApplication()).getWindowParams();

	windowManagerParams.type = LayoutParams.TYPE_TOAST;
	windowManagerParams.format = PixelFormat.RGBA_8888;
	windowManagerParams.flags = LayoutParams.FLAG_HARDWARE_ACCELERATED
		| LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
	windowManagerParams.gravity = Gravity.RIGHT | Gravity.TOP;
	windowManagerParams.x = 0;
	windowManagerParams.y = 0;
	windowManagerParams.width = LayoutParams.WRAP_CONTENT;
	windowManagerParams.height = LayoutParams.WRAP_CONTENT;
	mWindowManager.addView(mFloatView, windowManagerParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	if (intent != null) {
	    mPkgName = intent.getStringExtra("pkgName");
	    if (mTimer != null) {
		mTimer.cancel();
	    }

	    mTimer = new Timer();
	    mTimer.schedule(new TimerTask() {
		public void run() {
		    long mem = MemoryUtils.getPackageMemorySize(getApplicationContext(), mPkgName);
		    Message message = new Message();
		    message.getData().putLong("memory", mem);
		    mHandler.sendMessage(message);
		}
	    }, 0, Constants.DATA_REFRESH_TIME);
	}
	return super.onStartCommand(intent, flags, startId);
    }

}
