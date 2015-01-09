package com.example.hbjia.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {
    private static final String TAG = "FirstReceiver";
    public FirstReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        String message = intent.getStringExtra("msg");
        Log.d(TAG, "FirstReceiver :: " + message);

        Bundle bundle = new Bundle();
        bundle.putString("msg", message + "@FirstReceiver");
        setResultExtras(bundle);

        abortBroadcast();
    }
}
