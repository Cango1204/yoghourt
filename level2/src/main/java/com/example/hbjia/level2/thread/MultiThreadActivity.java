package com.example.hbjia.level2.thread;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbjia.level2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MultiThreadActivity extends Activity {

    private static int order = 0;

    //总共多少任务（根据CPU个数决定创建活动线程的个数,这样取的好处就是可以让手机承受得住）
    private static final int count = Runtime.getRuntime().availableProcessors() * 3 + 2;

//    private static final int count = 10;

    private static ExecutorService singleTaskExecutor = null;

    private static ExecutorService limitedTaskExecutor = null;

    /** 所有任务都一次性开始的线程池 */
    private static ExecutorService allTaskExecutor = null;

    private static ExecutorService scheduledTaskExecutor = null;

    private static ExecutorService scheduledTaskFactoryExecutor = null;

    private List<AsyncTaskTest> mTaskList;

    private boolean isCanceled = false;

    private boolean isClicked = false;

    ThreadFactory threadFactory = Executors.defaultThreadFactory();

    private static class ThreadFactoryTest implements ThreadFactory{

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("Cango_ThreadFactory");
            thread.setDaemon(true);
            return thread;
        }
    }

    static {
        singleTaskExecutor = Executors.newSingleThreadExecutor();
        limitedTaskExecutor = Executors.newFixedThreadPool(3);
        allTaskExecutor = Executors.newCachedThreadPool();
        scheduledTaskExecutor = Executors.newScheduledThreadPool(3);
        scheduledTaskFactoryExecutor = Executors.newFixedThreadPool(3, new ThreadFactoryTest());
        scheduledTaskFactoryExecutor.submit(new Runnable(){
            @Override
            public void run() {
                Log.i("Cango", "This is the ThreadFactory Test submit Run !!!!");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);
        final ListView taskList = (ListView) findViewById(R.id.task_list);
        taskList.setAdapter(new AsyncTaskAdapter(getApplication(), count));
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) {
                    List<Runnable> unExecRunn = allTaskExecutor.shutdownNow();
                    for(Runnable r : unExecRunn) {
                        Log.i("Cango", "未执行的任务信息：" + unExecRunn.toString());
                    }
                    Log.i("Cango", "Is shutdown ? = " + String.valueOf(allTaskExecutor.isShutdown()));
                    allTaskExecutor = null;
                }

                AsyncTaskTest sat = mTaskList.get(1);
                if(position == 1) {
                    if(!isClicked) {
                        sat.cancel(true);
                        isCanceled = true;
                        isClicked = true;
                    } else {
                        sat.cancel(false);
                        isCanceled = false;
                        isClicked = false;
                        if (null != sat && sat.getStatus() == AsyncTask.Status.RUNNING) {
                            if(sat.isCancelled()) {
                                sat = new AsyncTaskTest(sat.mTaskItem);
                            } else {
                                Toast.makeText(MultiThreadActivity.this, "A task is already running, try later", Toast.LENGTH_LONG).show();
                            }
                        }
                        if(allTaskExecutor == null) {
                            allTaskExecutor = Executors.newCachedThreadPool();
                            allTaskExecutor = Executors.newFixedThreadPool(3);
                        }
                        sat.executeOnExecutor(allTaskExecutor);
                    }
                }
                else {
                    sat.cancel(false);
                    isCanceled = false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_thread, menu);
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

    private class AsyncTaskAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private int mTaskCount;

        public AsyncTaskAdapter(Context context, int taskCount) {
            this.mInflater = LayoutInflater.from(context);
            this.mContext = context;
            this.mTaskCount = taskCount;
            mTaskList = new ArrayList<AsyncTaskTest>(taskCount);
        }

        @Override
        public int getCount() {
            return mTaskCount;
        }

        @Override
        public Object getItem(int i) {
            return mTaskList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View contentView, ViewGroup viewGroup) {
            if(contentView == null) {
                contentView = this.mInflater.inflate(R.layout.task_list_view_item, null);
                AsyncTaskTest task = new AsyncTaskTest((MyListItem) contentView);
                //以下两种方式效果一样，都是按顺序一个一个执行
//                task.execute();
//                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                //系统会默认的采用五个一组，五个一组的方式来执行我们的任务
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                //与前面的一样，一个一个执行任务
//                task.executeOnExecutor(singleTaskExecutor);

                //按我们指定的个数来执行任务的线程池
                task.executeOnExecutor(limitedTaskExecutor);

                //不限定指定个数的线程池，也就是说：你往里面放了几个任务，他全部同一时间开始执行， 不管你手机受得了受不了
//                task.executeOnExecutor(allTaskExecutor);

                //创建一个可在指定时间里执行任务的线程池，亦可重复执行
//                task.executeOnExecutor(scheduledTaskExecutor);

                //创建一个按指定工厂模式来执行任务的线程池,可能比较正规,但也不常用
//                task.executeOnExecutor(scheduledTaskFactoryExecutor);
                mTaskList.add(task);
            }
            return contentView;
        }
    }

    class AsyncTaskTest extends AsyncTask<Void, Integer, Void>{

        private MyListItem mTaskItem;
        private String id;

        private AsyncTaskTest(MyListItem item) {
            mTaskItem = item;
            if(order < count || order == count) {
                id = "执行：" + String.valueOf(++order);
            } else {
                order = 0;
                id = "执行：" + String.valueOf(++order);
            }
            Log.i("Cango", id);
        }

        @Override
        protected void onPreExecute() {
            mTaskItem.setTitle(id);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(!isCancelled() && !isCanceled) {
                int prog = 0;
                while (prog < 101) {
                    if ((prog > 0 || prog == 0) && prog < 70) {
                        SystemClock.sleep(100);
                    } else {
                        SystemClock.sleep(300);
                    }
                    publishProgress(prog);
                    prog++;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTaskItem.setProgress(values[0]);
        }
    }
}
