package com.example.hbjia.level2.thread;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2015/2/19.
 */
public class MyRunnable implements Runnable {

    private boolean cancelTask = false;
    private boolean cancelException = false;
    private Handler mHandler = null;

    public MyRunnable(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void run() {
        Log.i("Cango", "MyRunnable run() is executed !!!");
        runBefore();
        if (!cancelTask) {
            running();
            Log.i("Cango", "调用MyRunnable run()方法");
        }
        runAfter();
    }

    private void running() {
        Log.i("Cango", "running()");
        try{
            int prog = 0;
            if(!cancelTask && !cancelException) {
                while (prog < 101) {
                    if((prog > 0 || prog == 0) && prog < 70) {
                        SystemClock.sleep(100);
                    } else {
                        SystemClock.sleep(300);
                    }

                    if(!cancelTask) {
                        mHandler.sendEmptyMessage(prog++);
                        Log.i("Cango", "调用 prog++ = " + prog);
                    }
                }
            }
        }catch (Exception e) {

        }
    }

    private void runBefore() {
        Log.i("Cango", "runBefore()");
    }

    private void runAfter() {
        Log.i("Cango", "runAfter()");
    }

    public void setCancelTaskUnit(boolean cancelTask) {
        this.cancelTask = cancelTask;
    }
}
