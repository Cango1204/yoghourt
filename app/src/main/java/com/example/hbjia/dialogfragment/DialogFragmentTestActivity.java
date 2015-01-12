package com.example.hbjia.dialogfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hbjia.http.R;

public class DialogFragmentTestActivity extends Activity implements LoginDialogFragment.LoginInputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_framment_test);

    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.id_edit_name_dialog: {
                EditNameDialogFragment editNameDialogFragment = new EditNameDialogFragment();
                editNameDialogFragment.show(getFragmentManager(), "EditNameDialog");
                break;
            }
            case R.id.id_login_dialog: {
                LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.show(getFragmentManager(), "LoginDialog");
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_framment_test, menu);
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

    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, "用户名：" + username + " 密码：" + password, Toast.LENGTH_LONG).show();
    }
}
