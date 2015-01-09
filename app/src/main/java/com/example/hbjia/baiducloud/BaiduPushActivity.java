package com.example.hbjia.baiducloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.hbjia.http.R;

public class BaiduPushActivity extends Activity {

    private TextView pushMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_push);
        pushMessageView = (TextView) findViewById(R.id.push_message_view);

        autoBindBaiduYunTuiSong();
    }

    private void autoBindBaiduYunTuiSong() {
        if (!PreUtils.isBind(this))
        {
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    "eINZmDnrjyCg0RAbplEo0lk4");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("BaiduPushActivity", "onNewIntent");
        super.onNewIntent(intent);
        String result = intent.getStringExtra("result");
        if (result != null)
        {
            pushMessageView.setText(result);
        }
//        super.onNewIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baidu_push, menu);
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
