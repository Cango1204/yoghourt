package com.example.hbjia.level2.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2015/3/1.
 */
public class HandlerThread implements Runnable {

    private Handler handler;

    public HandlerThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
                Message message = this.handler.obtainMessage();
                message.what = i;
                this.handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
