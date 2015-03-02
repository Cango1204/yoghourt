package com.example.hbjia.level2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/3/1.
 */
public class FirstReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "I've received, " + intent.getStringExtra("data"), Toast.LENGTH_LONG).show();
        Log.e(this.getClass().getName(), "FirstReceiver received" + intent.getStringExtra("data"));
    }
}
