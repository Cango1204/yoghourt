package com.example.hbjia.http;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hbjia.adapter.OldAdapterActivity;
import com.example.hbjia.asynctask.AsyncActivity;
import com.example.hbjia.baiducloud.BaiduPushActivity;
import com.example.hbjia.contentprovider.SMSActivity;
import com.example.hbjia.customview.CustomViewActivity;
import com.example.hbjia.dialog.RightTopPopup;
import com.example.hbjia.fragment.ListTitleActivity;
import com.example.hbjia.handler.HandlerActivity;
import com.example.hbjia.layout.CustomLayoutActivity;
import com.example.hbjia.listview.ListViewActivity;
import com.example.hbjia.listview.RefreshListView;
import com.example.hbjia.notification.NotificationMain;
import com.example.hbjia.pager.ViewPagerDemo;
import com.example.hbjia.pager2.TraditionalViewPagerActivity;
import com.example.hbjia.prefs.PrefsActivity;
import com.example.hbjia.service.MyService;
import com.example.hbjia.sqlite.SqliteActivity;
import com.example.hbjia.sqlite.SqliteActivity2;
import com.example.hbjia.webview.WebViewActivity;
import com.example.zhy_baseadapterhelper.BaseAdapterActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    private MyService.MyBinder myBinder;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(this.getClass().getName(), "---onSaveInstanceState---");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(this.getClass().getName(), "---onConfigurationChanged---");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(this.getClass().getName(), "---onRestoreInstanceState---");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(this.getClass().getName(), "---onStart---");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(this.getClass().getName(), "---onRestart---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(this.getClass().getName(), "---onResume---");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(this.getClass().getName(), "---onPause---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(this.getClass().getName(), "---onStop---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(this.getClass().getName(), "---onDestroy---");
        this.unbind(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(this.getClass().getName(), "---onCreate---");
        setContentView(R.layout.main);

//        Button btn = (Button) findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        excute();
//                        Looper.loop();
//                    }
//                }).start();
//
//            }
//        });
//
//        Button orderedBroadcastBtn = (Button) findViewById(R.id.orderedBroadcast);
//        orderedBroadcastBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(OrderedBroadcast.class);
//            }
//        });
//
//        Button sqlite = (Button) findViewById(R.id.sqlite);
//        sqlite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(SqliteActivity.class);
//            }
//        });
//
//        Button sqlite2 = (Button) findViewById(R.id.sqlite2);
//        sqlite2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(SqliteActivity2.class);
//            }
//        });
//
//        Button sms = (Button) findViewById(R.id.smsActivity);
//        sms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(SMSActivity.class);
//            }
//        });
//
//        Button handlerActivityBtn = (Button) findViewById(R.id.handlerActivity);
//        handlerActivityBtn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(HandlerActivity.class);
//            }
//        });
//
////        Button listViewActivityBtn = (Button) findViewById(R.id.listViewActivity);
////        listViewActivityBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                startSpecifiedActivity(ListViewActivity.class);
////            }
////        });
//
//        Button asyncActivity = (Button) findViewById(R.id.asyncTask);
//        asyncActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(AsyncActivity.class);
//            }
//        });
//
//        Button prefsActivity = (Button) findViewById(R.id.prefs);
//        prefsActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startSpecifiedActivity(PrefsActivity.class);
//            }
//        });

        Button notificationActivity = (Button) findViewById(R.id.notificationBtn);
        notificationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpecifiedActivity(NotificationMain.class);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewPagerDemo:
//                startSpecifiedActivity(ViewPagerDemo.class);
//                startSpecifiedActivity(RightTopPopup.class);
//                startSpecifiedActivity(RefreshListView.class);
//                startSpecifiedActivity(BaiduPushActivity.class);
//                startSpecifiedActivity(BaseAdapterActivity.class);
//                startSpecifiedActivity(ListTitleActivity.class);
//                startSpecifiedActivity(CustomLayoutActivity.class);
//                startSpecifiedActivity(CustomViewActivity.class);
                startSpecifiedActivity(TraditionalViewPagerActivity.class);
                break;
            case R.id.webViewBtn:
                startSpecifiedActivity(WebViewActivity.class);
                break;
        }
    }

    private void startSpecifiedActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void excute() {
        String PATH = "http://10.66.40.42:8080/AjaxTest/ajax.do?firstname=Jia&age=32";
        MyApplication myApp = (MyApplication) this.getApplication();
        HttpClient httpClient = myApp.getHttpClient();
        HttpGet httpGet = new HttpGet(PATH);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = response.getEntity().getContent();
                String result = inputStream2String(in);
                Log.v(this.getClass().getName(), result);
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startService(View view){
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, MyService.class);
        this.stopService(intent);
    }

    private boolean binded;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(this.getClass().getName(), "onServiceConnected====");
            binded = true;
            myBinder = (MyService.MyBinder) iBinder;
            myBinder.greet("Cango");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public void bind(View view){
        Intent intent = new Intent(this, MyService.class);
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbind(View view){
        if(binded) {
            this.unbindService(serviceConnection);
            binded = false;
        }
    }

    public void send(View view) {
        Intent intent = new Intent("android.intent.action.MY_RECEIVER");
        intent.putExtra("msg", "Hello, my receiver");
//        this.sendBroadcast(intent);
        this.sendOrderedBroadcast(intent, "hbjia.broadcast.permission");
    }

    private String inputStream2String(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte [] buf = new byte[1024];
        int len = -1;
        while((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_http, menu);
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
