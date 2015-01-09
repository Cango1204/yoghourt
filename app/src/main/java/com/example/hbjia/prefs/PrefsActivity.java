package com.example.hbjia.prefs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hbjia.http.R;

public class PrefsActivity extends Activity {

    private static final int SETTINGS_ID = 0;
    private static final int EXIT_ID = 1;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        textView = (TextView) findViewById(R.id.textView);
        showSettings();
    }

    private void showSettings() {
        String prefsName = getPackageName() + "_preferences";
        SharedPreferences prefs = getSharedPreferences(prefsName, MODE_PRIVATE);

        String nickName = prefs.getString("nickName", "Robot");
        textView.setText("Welcome : " + nickName);

        boolean nightMode = prefs.getBoolean("nightMode", false);
        textView.setBackgroundColor(nightMode ? Color.BLACK : Color.WHITE);

        String textSize = prefs.getString("textSize", "0");
        if(textSize.equals("0")) {
            textView.setTextSize(18f);
        } else if (textSize.equals("1")) {
            textView.setTextSize(22f);
        } else if (textSize.equals("2")) {
            textView.setTextSize(36f);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prefs, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1);
        } else {
            finish();
        }

        return true;
    }
}
