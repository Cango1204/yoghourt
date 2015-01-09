package com.example.hbjia.contentprovider;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hbjia.http.R;

import java.util.List;

public class SMSActivity extends Activity {

    private SendReceiver sendReceiver = new SendReceiver();
    private DeliverReceiver deliverReceiver = new DeliverReceiver();

    private EditText address;
    private EditText body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        address = (EditText) findViewById(R.id.address);
        body = (EditText) findViewById(R.id.body);

        registerReceiver(sendReceiver, new IntentFilter("SENT_SMS_ACTION"));
        registerReceiver(deliverReceiver, new IntentFilter("DELIVERED_SMS_ACTION"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sm, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(sendReceiver);
        unregisterReceiver(deliverReceiver);
    }

    public void sendSMS(View view) {
        String phoneNumber = this.address.getText().toString();
        String message = this.body.getText().toString();

        SmsManager manager = SmsManager.getDefault();

        PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, new Intent("SENT_SMS_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("DELIVERED_SMS_ACTION"), 0);

        if(message.length() > 70) {
            List<String> msgs = manager.divideMessage(message);
            for(String msg : msgs) {
                if(msg != null) {
                    manager.sendTextMessage(phoneNumber, null, msg, sendIntent, deliveredIntent);
                }
            }
        } else {
            manager.sendTextMessage(phoneNumber, null, message, sendIntent, deliveredIntent);
        }

        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("body", message);
        values.put("date", System.currentTimeMillis());
        values.put("read", 0);
        values.put("type", 2);
        getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }

    class SendReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case RESULT_OK:
                    Toast.makeText(context, "Sent Successfully.", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Fail to send.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 发送方的短信发送到对方手机上之后,对方手机会返回给运营商一个信号,
     * 运营商再把这个信号发给发送方,发送方此时可确认对方接收成功
     * 模拟器不支持,真机上需等待片刻
     * @author user
     *
     */

    class DeliverReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Delivered Successfully.", Toast.LENGTH_LONG).show();
        }
    }
}
