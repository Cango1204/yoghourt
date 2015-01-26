package com.example.hbjia.level2.com.example.hbjia.level2.asynctaskandprogress;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.example.hbjia.level2.R;

import java.util.Arrays;
import java.util.List;

public class FixProblemActivity extends ListActivity {

    private static String TAG = "FixProblemActivity";
    private ListAdapter mAdapter;
    private List<String> mDatas;
    private OtherRetainedFragment otherRetainedFragment;
    private MyAsyncTask mMyAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = this.getFragmentManager();
        otherRetainedFragment = (OtherRetainedFragment) fm.findFragmentByTag("data");

        if(otherRetainedFragment == null) {
            otherRetainedFragment = new OtherRetainedFragment();
            fm.beginTransaction().add(otherRetainedFragment, "data").commit();
        }
        mMyAsyncTask = otherRetainedFragment.getMyAsyncTask();
        if(mMyAsyncTask != null) {
            mMyAsyncTask.setActivity(this);
        } else {
            mMyAsyncTask = new MyAsyncTask(this);
            otherRetainedFragment.setMyAsyncTask(mMyAsyncTask);
            mMyAsyncTask.execute();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMyAsyncTask.setActivity(null);
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fix_problem, menu);
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

    public void onTaskCompleted() {
        mDatas = mMyAsyncTask.getItems();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(mAdapter);
    }
}
