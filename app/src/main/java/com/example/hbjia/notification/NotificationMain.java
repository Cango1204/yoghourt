package com.example.hbjia.notification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hbjia.http.R;

public class NotificationMain extends Activity {

    private boolean binded;
    private DownloadService.DownloadBinder binder;
    private TextView text;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progress = msg.arg1;
            text.setText("downloading..." + progress + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);
        text = (TextView) findViewById(R.id.text);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (DownloadService.DownloadBinder) iBinder;
            binded = true;

            binder.start();
            listenProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void listenProgress() {
        new Thread(){
            public void run() {
                while (!binder.isCancelled() && binder.getProgress() <= 100) {
                    int progress = binder.getProgress();
                    Message message = handler.obtainMessage();
                    message.arg1 = progress;
                    handler.sendMessage(message);
                    if(progress == 100) {
                        break;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_main, menu);
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

    public void start(View view) {
        if(binded) {
            binder.start();
            listenProgress();
            return;
        }
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binded) {
            unbindService(conn);
        }
    }
}
