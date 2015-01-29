package com.example.hbjia.level2.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hbjia.level2.R;

/**
 * Created by hbjia on 2015/1/29.
 */
public class TitleView extends FrameLayout {

    private Button mLeftButton;
    private TextView mTitleText;

    public TitleView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.title, this);
        mLeftButton = (Button) findViewById(R.id.id_leftButton);
        mTitleText = (TextView) findViewById(R.id.id_titleText);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setTitleText(String titleText) {
        mTitleText.setText(titleText);
    }

    public void setButtonText(String buttonText) {
        mLeftButton.setText(buttonText);
    }

    public void setClickListener(OnClickListener listener) {
        this.setOnClickListener(listener);
    }
}
