package com.example.hbjia.level2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.hbjia.level2.asynctaskandprogress.FixProblemActivity;
import com.example.hbjia.level2.asynctaskandprogress.FragmentRetainDataActivity;
import com.example.hbjia.level2.asynctaskandprogress.SavedInstanceStateUsingActivity;
import com.example.hbjia.level2.crash.CrashMainActivity;
import com.example.hbjia.level2.customview.DeleteListActivity;
import com.example.hbjia.level2.customview.TitleView;
import com.example.hbjia.level2.imageviewer.ImageViewActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends Activity {

    private String mStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = mStr.equals("FUCK");
            }
        }).start();

        TitleView titleView = (TitleView) findViewById(R.id.id_titleView);
        titleView.setButtonText("返回");
        titleView.setTitleText("微信");
        titleView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Title 被点击", Toast.LENGTH_LONG).show();
            }
        });
//        forceShowOverflowMenu();
    }

    public void startSavedInstanceStateUsingActivity(View v) {
        Intent intent = new Intent(this, SavedInstanceStateUsingActivity.class);
        startActivity(intent);
    }

    public void startFragmentRetainDataActivity(View v) {
        Intent intent = new Intent(this, FragmentRetainDataActivity.class);
        startActivity(intent);
    }

    public void startFixProblemActivity(View v) {
        Intent intent = new Intent(this, FixProblemActivity.class);
        startActivity(intent);
    }

    public void startImageViewActivity(View v) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        startActivity(intent);
    }

    public void startDeleteListActivity(View v) {
        Intent intent = new Intent(this, DeleteListActivity.class);
        startActivity(intent);
    }

    public void startCrashMainActivity(View v) {
        Intent intent = new Intent(this, CrashMainActivity.class);
        startActivity(intent);
    }

    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.e("TAG", "Max memory is " + maxMemory + "KB");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add :
                Toast.makeText(this, "Add pressed", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(menu != null) {
            if(menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
