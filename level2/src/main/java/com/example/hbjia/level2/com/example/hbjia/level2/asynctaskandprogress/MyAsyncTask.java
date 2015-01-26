package com.example.hbjia.level2.com.example.hbjia.level2.asynctaskandprogress;

import android.app.DialogFragment;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hbjia on 2015/1/26.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    private FixProblemActivity activity;
    private boolean isCompleted;
    private DialogFragment mLoadingDialog;
    private List<String> items;

    public MyAsyncTask(FixProblemActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(activity.getFragmentManager(), "LOADING");
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

    @Override
    protected Void doInBackground(Void... voids) {
        items = generateTimeConsumingDatas();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        isCompleted = true;
        notifyActivityTaskCompleted();
        if(mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public List<String> getItems() {
        return items;
    }

    private void notifyActivityTaskCompleted() {
        if(activity != null) {
            activity.onTaskCompleted();
        }
    }

    public void setActivity(FixProblemActivity activity) {
        if(activity == null) {
            mLoadingDialog.dismiss();
        }
        this.activity = activity;
        if(activity != null) {
            mLoadingDialog = new LoadingDialog();
            mLoadingDialog.show(this.activity.getFragmentManager(), "LOADING");
        }
        if(isCompleted) {
            notifyActivityTaskCompleted();
        }
    }
}
