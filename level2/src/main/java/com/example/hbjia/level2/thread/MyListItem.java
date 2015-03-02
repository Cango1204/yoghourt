package com.example.hbjia.level2.thread;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hbjia.level2.R;

/**
 * Created by Administrator on 2015/2/19.
 */
public class MyListItem extends LinearLayout {

    private TextView mTitle;
    private ProgressBar mProgressBar;

    public MyListItem(Context context) {
        super(context);
    }

    public MyListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTitle(String title) {
        if(mTitle == null) {
            mTitle = (TextView) findViewById(R.id.task_name);
        }
        mTitle.setText(title);
    }

    public void setProgress(int progress) {
        if(mProgressBar == null) {
            mProgressBar = (ProgressBar) findViewById(R.id.task_progress);
        }
        mProgressBar.setProgress(progress);
    }
}
