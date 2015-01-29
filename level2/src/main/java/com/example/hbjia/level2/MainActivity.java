package com.example.hbjia.level2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hbjia.level2.asynctaskandprogress.FixProblemActivity;
import com.example.hbjia.level2.asynctaskandprogress.FragmentRetainDataActivity;
import com.example.hbjia.level2.asynctaskandprogress.SavedInstanceStateUsingActivity;
import com.example.hbjia.level2.customview.DeleteListActivity;
import com.example.hbjia.level2.customview.TitleView;
import com.example.hbjia.level2.imageviewer.ImageViewActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TitleView titleView = (TitleView) findViewById(R.id.id_titleView);
        titleView.setButtonText("返回");
        titleView.setTitleText("微信");
        titleView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Title 被点击", Toast.LENGTH_LONG).show();
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
