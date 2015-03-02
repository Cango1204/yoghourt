package com.jason.test.view;

import com.jason.test.R;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FloatView extends RelativeLayout {

    private TextView mMemTextView;
    private TextView mPkgNameTextView;

    public FloatView(Context context) {
	super(context);
	initViews();
    }

    private void initViews() {
	inflate(getContext(), R.layout.floating_layout, this);
	mMemTextView = (TextView) findViewById(R.id.meminfo_text);
	mPkgNameTextView = (TextView) findViewById(R.id.pkgname_text);
    }

    public void setMemInfo(String text) {
	if(TextUtils.isEmpty(text)){
	    mMemTextView.setText(R.string.debug_app_not_running);
	}else {
	    mMemTextView.setText(getResources().getString(R.string.memeroy_usage) + text);
	}
    }

    public void setPkgName(String text) {
	mPkgNameTextView.setText(text);
    }

}