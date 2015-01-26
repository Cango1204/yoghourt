package com.example.hbjia.level2.asynctaskandprogress;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.hbjia.level2.R;

import java.util.ArrayList;
import java.util.Arrays;

//验证Activity在不明确指定屏幕方向和configChanges时，当用户旋转屏幕会重新启动，对数据的保存与恢复
public class SavedInstanceStateUsingActivity extends ListActivity {

    private static String TAG = "SavedInstanceStateUsingActivity";
    private ListAdapter mAdapter;
    private ArrayList<String> mDatas;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saved_instance_state_using);
        Log.e(TAG, "onCreate");
        initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            mDatas = savedInstanceState.getStringArrayList("mDatas");
        }
        if(mDatas == null) {
            Log.e(TAG, "Load data.....");
            LoadDataAsyncTask loadDataAsyncTask = new LoadDataAsyncTask();
            loadDataAsyncTask.execute();
        } else {
            initAdapter();
        }
    }

    private void initAdapter() {
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(mAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Portrait", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putSerializable("mDatas", mDatas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_instance_state_using, menu);
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

    private ArrayList<String> generateTimeConsumingDatas() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList("Java", "C++", "Objective-C"
            , "PHP", "JavaScript"));
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private DialogFragment mLoadingDialog;
        public LoadDataAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            mLoadingDialog = new LoadingDialog();
            mLoadingDialog.show(getFragmentManager(), "LoadingDialog");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDatas = generateTimeConsumingDatas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mLoadingDialog == null) {
                mLoadingDialog = (DialogFragment) getFragmentManager().findFragmentByTag("LoadingDialog");
            }
            mLoadingDialog.dismiss();
            initAdapter();
        }
    }
}
