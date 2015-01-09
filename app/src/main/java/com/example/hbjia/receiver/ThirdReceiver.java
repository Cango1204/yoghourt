package com.example.hbjia.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ThirdReceiver extends BroadcastReceiver {
    private static final String TAG = "ThirdReceiver";
    public ThirdReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        String message = this.getResultExtras(true).getString("msg");
        Log.d(TAG, "ThirdReceiver :: " + message);
    }
}
