package com.example.hbjia.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hbjia.http.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsyncActivity extends Activity {

    private static final String TAG = "ASYNC_TASK";

    private Button execute;
    private Button cancel;
    private ProgressBar progressBar;
    private TextView textView;
    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        execute = (Button) findViewById(R.id.execute);
        cancel = (Button) findViewById(R.id.cancel);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTask = new MyTask();
                myTask.execute("http://img.my.csdn.net/uploads/201412/10/1418181338_1048.png");
                execute.setEnabled(false);
                cancel.setEnabled(true);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTask.cancel(true);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.text_view);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground called");
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(strings[0]);
                HttpResponse response = httpClient.execute(httpGet);
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream is = response.getEntity().getContent();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte [] buf = new byte[1024];
                    long total = response.getEntity().getContentLength();
                    int len = -1;
                    int count = 0;
                    while ((len = is.read(buf)) != -1){
                        baos.write(buf, 0, len);
                        count += len;
                        int rate = (int) ((count / (float) total) * 100);
                        Log.i(TAG, "total === " + total);
                        publishProgress(rate);
                        Thread.sleep(500);
                    }
                    return new String(baos.toByteArray(), "gb2312");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute called");
            textView.setText(s);

            execute.setEnabled(true);
            cancel.setEnabled(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate called");
            progressBar.setProgress(values[0]);
            textView.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled called");
            textView.setText("canceled");

            progressBar.setProgress(0);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_async, menu);
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
