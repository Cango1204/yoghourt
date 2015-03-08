package com.example.hbjia.level2.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015/3/8.
 */
public class EventView extends RelativeLayout implements View.OnTouchListener{
    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(this.getClass().getName(), "View group ++++ onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(this.getClass().getName(), "View group ++++ dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(this.getClass().getName(), "View group ++++ onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(this.getClass().getName(), "View group +++++ onTouchEvent");
        return false;
    }
}
