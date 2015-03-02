package com.example.hbjia.level2.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/3/2.
 */
public class SubThread extends Thread {

    private Handler handler;
    private Handler uiHandler;
    private Context mContext;

    public SubThread(Handler handler) {
        this.uiHandler = handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                Toast.makeText(mContext, "UI thread send you message", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = uiHandler.obtainMessage();
                message.what = 222;
                uiHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }

    public Handler getHandler(){
        return this.handler;
    }
}
