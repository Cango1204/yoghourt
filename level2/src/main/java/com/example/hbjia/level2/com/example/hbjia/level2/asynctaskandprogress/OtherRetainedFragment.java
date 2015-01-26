package com.example.hbjia.level2.com.example.hbjia.level2.asynctaskandprogress;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by hbjia on 2015/1/26.
 */
public class OtherRetainedFragment extends Fragment{

    private MyAsyncTask myAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public MyAsyncTask getMyAsyncTask() {
        return myAsyncTask;
    }

    public void setMyAsyncTask(MyAsyncTask myAsyncTask) {
        this.myAsyncTask = myAsyncTask;
    }
}
