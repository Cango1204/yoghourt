package com.example.hbjia.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hbjia.http.R;
import com.hbjia.aidl.IMyAidlInterface;

public class AIDLActivity extends Activity {

    private final static String TAG = "AIDLActivity";
    private IMyAidlInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_aidl_ok:{
                Intent intent = new Intent("com.hbjia.aidl.service");
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            }
            case R.id.id_aidl_cancel:{
                unbindService(serviceConnection);
                break;
            }
            case R.id.id_aidl_callback:{
                Log.i(TAG, "current thread is = " + Thread.currentThread().getId());
                try {
                    mService.invokeTest();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "service connected");
            mService =  IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "service disconnected");
            mService = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aidl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
