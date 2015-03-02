package com.example.hbjia.level2.thread;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hbjia.level2.R;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyRunnableActivity extends Activity implements View.OnClickListener {

    private ConcurrentLinkedQueue<MyRunnable> taskQueue = null;

    private ConcurrentMap<Future, MyRunnable> taskMap = null;

    private ExecutorService mES = null;

    private Object lock = new Object();

    private boolean isNotify = true;

    private boolean isRunning = true;

    private ProgressBar progressBar = null;

    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_runnable_main);
        init();
    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        taskQueue = new ConcurrentLinkedQueue<MyRunnable>();
        taskMap = new ConcurrentHashMap<Future, MyRunnable>();
        if(mES == null) {
            mES = Executors.newCachedThreadPool();
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressBar.setProgress(msg.what);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_runnable, menu);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                start();
                break;
            case R.id.button2:
                stop();
                break;
            case R.id.button3:
                reload(new MyRunnable(mHandler));
                break;
            case R.id.button4:
                release();
                break;
            case R.id.button5:
                addTask(new MyRunnable(mHandler));
                break;
            default:
                break;
        }
    }

    private void notifyWork() {
        synchronized (lock) {
            if (isNotify) {
                lock.notifyAll();
                isNotify = false;
            }
        }
    }

    private void addTask(final MyRunnable myRunnable) {
        mHandler.sendEmptyMessage(0);
        if(mES == null) {
            mES = Executors.newCachedThreadPool();
            notifyWork();
        }

        if(taskQueue == null) {
            taskQueue = new ConcurrentLinkedQueue<MyRunnable>();
        }

        if(taskMap == null) {
            taskMap = new ConcurrentHashMap<Future, MyRunnable>();
        }

        mES.execute(new Runnable() {
            @Override
            public void run() {
                taskQueue.offer(myRunnable);
                notifyWork();
            }
        });

        Toast.makeText(MyRunnableActivity.this, "已添加一个新的任务到线程池中！", Toast.LENGTH_LONG).show();
    }

    private void start() {
        if (mES == null || taskQueue == null || taskMap == null) {
            Log.i("Cango", "某资源是不是已经被释放了？");
            return;
        }

        mES.execute(new Runnable() {
            @Override
            public void run() {
                if(isRunning) {
                    MyRunnable myRunnable = null;
                    synchronized (lock) {
                        myRunnable = taskQueue.poll();
                        if(myRunnable == null) {
                            isNotify = true;

//                            try {
//                                lock.wait(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(myRunnable != null) {
                        taskMap.put(mES.submit(myRunnable), myRunnable);
                    }
                }
            }
        });
    }

    private void stop() {
        Toast.makeText(MyRunnableActivity.this, "任务已被取消！", Toast.LENGTH_LONG).show();
        if (taskMap != null) {
            for(MyRunnable runnable : taskMap.values()) {
                runnable.setCancelTaskUnit(true);
            }
        }
    }

    private void reload(final MyRunnable myRunnable) {
        mHandler.sendEmptyMessage(0);
        if(mES == null) {
            mES = Executors.newCachedThreadPool();
        }

        if(taskQueue == null) {
            taskQueue = new ConcurrentLinkedQueue<MyRunnable>();
        }

        if(taskMap == null) {
            taskMap = new ConcurrentHashMap<Future, MyRunnable>();
        }

        mES.execute(new Runnable() {
            @Override
            public void run() {
                taskQueue.offer(myRunnable);
                notifyWork();
            }
        });

        mES.execute(new Runnable() {
            @Override
            public void run() {
                if(isRunning) {
                    MyRunnable myRunnable = null;
                    synchronized (lock) {
                        myRunnable = taskQueue.poll();
                        if(myRunnable == null) {
                            isNotify = true;

                        }
                    }

                    if(myRunnable != null) {
                        taskMap.put(mES.submit(myRunnable), myRunnable);
                    }
                }
            }
        });
    }

    private void release() {
        Toast.makeText(MyRunnableActivity.this, "释放所有资源！", Toast.LENGTH_LONG).show();;

        mHandler.sendEmptyMessage(0);
        isRunning = false;

        Iterator iterator = taskMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Future, MyRunnable> entry = (Map.Entry<Future, MyRunnable>) iterator.next();
            Future result = entry.getKey();
            if(result == null) {
                continue;
            }
            result.cancel(true);
            taskMap.remove(result);
        }

        if (null != mES) {
            mES.shutdown();
        }

        mES = null;
        taskMap = null;
        taskQueue = null;
//        notifyWork();
    }
}
