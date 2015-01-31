package com.example.hbjia.level2.windowmanager;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/1/31.
 */
public class FloatView extends View {

    private static final String TAG = "FloatView";
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private WindowManager mWindowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams mLayoutParams = ((FloatApplication)getContext().getApplicationContext()).getLayoutParams();

    public FloatView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY() - 25;
        Log.i(TAG, "currentX = " + x + "currentY = " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition();
                mTouchStartX = 0;
                mTouchStartY = 0;
                break;
        }
        return true;
    }

    private void updateViewPosition() {
        mLayoutParams.x = (int) (x - mTouchStartX);
        mLayoutParams.y = (int) (y - mTouchStartY);
        mWindowManager.updateViewLayout(this, mLayoutParams);
    }
}
