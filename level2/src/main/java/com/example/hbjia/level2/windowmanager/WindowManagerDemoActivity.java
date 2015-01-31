package com.example.hbjia.level2.windowmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.hbjia.level2.R;

public class WindowManagerDemoActivity extends Activity {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private FloatView mFloatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_window_manager_demo);
        showView();
    }

    private void showView() {
        mFloatView = new FloatView(getApplicationContext());
        mFloatView.setBackgroundResource(R.drawable.dialog_loading_bg);
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        mLayoutParams = ((FloatApplication)getApplication()).getLayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.format = 1;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mLayoutParams.flags = mLayoutParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        mLayoutParams.alpha = 1.0f;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mLayoutParams.width = 140;
        mLayoutParams.height = 140;

        mWindowManager.addView(mFloatView, mLayoutParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mFloatView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_window_manager_demo, menu);
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
}
