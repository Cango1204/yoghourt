package com.example.hbjia.level2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2015/3/1.
 */
public class SecondReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(this.getClass().getName(), "SecondReceiver received" + intent.getStringExtra("data"));
    }
}
