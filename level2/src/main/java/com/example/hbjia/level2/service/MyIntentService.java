package com.example.hbjia.level2.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2015/3/1.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
                Log.i(this.getClass().getName(), intent.getStringExtra("name") + "  == " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
