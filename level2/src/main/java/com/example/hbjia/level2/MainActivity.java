package com.example.hbjia.level2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.hbjia.level2.appinfo.AppInfoActivity;
import com.example.hbjia.level2.asynctaskandprogress.FixProblemActivity;
import com.example.hbjia.level2.asynctaskandprogress.FragmentRetainDataActivity;
import com.example.hbjia.level2.asynctaskandprogress.SavedInstanceStateUsingActivity;
import com.example.hbjia.level2.crash.CrashMainActivity;
import com.example.hbjia.level2.customview.DeleteListActivity;
import com.example.hbjia.level2.customview.TitleView;
import com.example.hbjia.level2.handler.HandlerThread;
import com.example.hbjia.level2.handler.SubThread;
import com.example.hbjia.level2.imageviewer.ImageViewActivity;
import com.example.hbjia.level2.opengl.OpenGLActivity;
import com.example.hbjia.level2.receiver.FirstReceiver;
import com.example.hbjia.level2.service.FirstService;
import com.example.hbjia.level2.service.MyIntentService;
import com.example.hbjia.level2.socket.ChatClientActivity;
import com.example.hbjia.level2.thread.MultiThreadActivity;
import com.example.hbjia.level2.thread.MyRunnableActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends Activity {

    private String mStr;
    private BroadcastReceiver firstReceiver;
    private TitleView titleView;
    private SubThread subThread;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            if(msg.what == 101) {
//                Toast.makeText(MainActivity.this, "begin handleMessage", Toast.LENGTH_SHORT).show();
//            }
//            Log.e(this.getClass().getName(), "Count ---- " + msg.what);
            titleView.setButtonText(msg.what + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = mStr.equals("FUCK");
//            }
//        }).start();

        titleView = (TitleView) findViewById(R.id.id_titleView);
        titleView.setButtonText("返回");
        titleView.setTitleText("微信");
        titleView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Title 被点击", Toast.LENGTH_LONG).show();
            }
        });
//        forceShowOverflowMenu();
        firstReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "I've also received, " + intent.getStringExtra("data"), Toast.LENGTH_LONG).show();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        for(int i = 0; i < 20; i++) {
//                            try {
//                                Thread.sleep(1000);
//                                Log.e("FirstReceiver", "count : " + i);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }.start();
//                Intent toIntent = new Intent(context, MyIntentService.class);
//                toIntent.putExtra("name", intent.getStringExtra("data"));
//                startService(toIntent);
                titleView.setButtonText(intent.getStringExtra("data"));
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hbjia.first.receiver.null");
        this.registerReceiver(firstReceiver, intentFilter);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DeleteListActivity.class);
//        Intent intent = new Intent(this, FirstService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
//        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 5000, pendingIntent);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 8000, pendingIntent);
        subThread = new SubThread(this.handler);
        subThread.start();
    }

    public void startSavedInstanceStateUsingActivity(View v) {
        Intent intent = new Intent(this, SavedInstanceStateUsingActivity.class);
        startActivity(intent);
    }

    public void startFragmentRetainDataActivity(View v) {
        Intent intent = new Intent(this, FragmentRetainDataActivity.class);
        startActivity(intent);
    }

    public void startFixProblemActivity(View v) {
        Intent intent = new Intent(this, FixProblemActivity.class);
        startActivity(intent);
    }

    public void startImageViewActivity(View v) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        startActivity(intent);
    }

    public void startDeleteListActivity(View v) {
        Intent intent = new Intent(this, DeleteListActivity.class);
        startActivity(intent);
    }

    public void startCrashMainActivity(View v) {
        Intent intent = new Intent(this, CrashMainActivity.class);
        startActivity(intent);
    }

    public void startAppInfoActivity(View v) {
        Intent intent = new Intent(this, AppInfoActivity.class);
        startActivity(intent);
    }

    public void startMultiThreadActivity(View v) {
        Intent intent = new Intent(this, MultiThreadActivity.class);
        startActivity(intent);
    }

    public void startCustomMultiThreadActivity(View v) {
        Intent intent = new Intent("com.hbjia.myrunnable");
        startActivity(intent);
    }

    public void sendBroadCastToFirst(View v){
        Intent intent = new Intent("com.hbjia.first.receiver.null");
        intent.putExtra("data", "cango");
        this.sendBroadcast(intent);
//        this.sendOrderedBroadcast(intent, "com.hbjia.receiver.permission");
    }

    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isBinded = false;
    private Messenger mainMessager;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBinded = true;
//            ((FirstService.MyBinder)iBinder).callMyName();
            mainMessager = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBinded = false;
            mainMessager = null;
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_first_service_start:{
                Intent intent = new Intent(this, FirstService.class);
                this.startService(intent);
                break;
            }
            case R.id.id_first_service_stop:{
                Intent intent = new Intent(this, FirstService.class);
                this.stopService(intent);
                break;
            }
            case R.id.id_bind_first_service:{
                bind();
                break;
            }
            case R.id.id_unbind_first_service:{
                unBind();
                break;
            }
            case R.id.id_handler: {
//                new Thread(){
//                    @Override
//                    public void run() {
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                titleView.setButtonText("搞什么");
//                            }
//                        }, 5000);
//                    }
//                }.start();
//                new Thread(new HandlerThread(this.handler)).start();
//                new Thread(new HandlerThread(this.handler)).start();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        Looper.prepare(); //创建消息队列
//                        subThreadLooper = Looper.myLooper();
//                        subHandler = new Handler(subThreadLooper){
//                            @Override
//                            public void handleMessage(Message msg) {
//                                titleView.setButtonText("搞么");
//                            }
//                        };
//                        Looper.loop(); //进入消息循环
//                    }
//                }.start();
                subThread.getHandler().sendEmptyMessage(10);
                break;
            }
            case R.id.id_subhandler: {

//                subHandler.sendEmptyMessage(1);
                Message message = Message.obtain(null, 121);
                try {
                    message.replyTo = new Messenger(handler);
                    mainMessager.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.id_opengl: {
                Intent intent = new Intent(this, OpenGLActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.id_socket: {
                Intent intent = new Intent(this, ChatClientActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void bind(){
        Intent intent = new Intent(this, FirstService.class);
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void unBind(){
        if(isBinded){
            this.unbindService(serviceConnection);
            isBinded = false;
        }
    }

    private void acquireWakeLock(){
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakeLock.acquire();
    }

    @Override
    protected void onResume() {
//        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//        Log.e("TAG", "Max memory is " + maxMemory + "KB");
        super.onResume();
        acquireWakeLock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(firstReceiver);
        this.unBind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add :
                Toast.makeText(this, "Add pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete :
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(menu != null) {
            if(menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
