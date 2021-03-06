package com.example.hbjia.level2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/3/1.
 */
public class FirstService extends Service {

    private static String TAG = "FirstService";

    private IBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(){
            @Override
            public void run() {
//                for(int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(3000);
//                        Log.i("FirstService", "count :: " + i);
                        Intent broadCastIntent = new Intent("com.hbjia.first.receiver.null");
                        broadCastIntent.putExtra("data", "Cango");
                        sendBroadcast(broadCastIntent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                }
            }
        }.start();
        Log.i(TAG, "service onStartCommand");
        return START_STICKY; //It means the service will be restarted automatically if it was killed by system.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "service onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "service onBind");
        return messenger.getBinder();
    }

    public class MyBinder extends Binder{

        public void callMyName(){
            Toast.makeText(FirstService.this, "I'm cango here", Toast.LENGTH_SHORT).show();
        }

    }

    private Messenger outMessenger;

    private Messenger messenger = new Messenger(new IncomingHandler());

    private void sendMessageToActivity(){
        Message message = new Message();
        message.what = 321;
        try {
            outMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(FirstService.this, "Received message from activity!", Toast.LENGTH_SHORT).show();
            outMessenger = msg.replyTo;
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            sendMessageToActivity();
        }
    }
}
