package com.example.hbjia.level2.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2015/3/8.
 */
public class EventButton extends Button implements View.OnTouchListener{
    public EventButton(Context context) {
        super(context);
        this.setOnTouchListener(this);
    }

    public EventButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(this.getClass().getName(), "View --- onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(this.getClass().getName(), "View --- dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(this.getClass().getName(), "View --- onTouch");
        return true;
    }
}
