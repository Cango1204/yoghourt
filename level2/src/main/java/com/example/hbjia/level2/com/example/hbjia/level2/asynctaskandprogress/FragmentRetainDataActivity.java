package com.example.hbjia.level2.com.example.hbjia.level2.asynctaskandprogress;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.hbjia.level2.R;

public class FragmentRetainDataActivity extends Activity {

    private static String TAG = "FragmentRetainDataActivity";
    private RetainedFragment retainedFragment;
    private DialogFragment mLoadingDialog;
    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_retain_data);
        Log.e(TAG, "onCreate");

        FragmentManager fm = getFragmentManager();
        retainedFragment = (RetainedFragment)fm.findFragmentByTag("data");

        if(retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            fm.beginTransaction().add(retainedFragment, "data").commit();
        }

        mBitmap = collectMyLoadedData();
        initData();

    }

    private void initData() {
        mImageView = (ImageView) findViewById(R.id.id_imageView);
        if(mBitmap == null) {
            mLoadingDialog = new LoadingDialog();
            mLoadingDialog.show(getFragmentManager(), "LOADING_DIALOG");
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ImageRequest imageRequest = new ImageRequest(
                    "http://img.my.csdn.net/uploads/201407/18/1405652589_5125.jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            mBitmap = response;
                            mImageView.setImageBitmap(mBitmap);
                            retainedFragment.setData(mBitmap);
                            mLoadingDialog.dismiss();
                        }
                    },0 ,0 , Bitmap.Config.RGB_565, null);
            requestQueue.add(imageRequest);
        } else {
            mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        retainedFragment.setData(mBitmap);
    }

    private Bitmap collectMyLoadedData() {
        return retainedFragment.getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_retain_data, menu);
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
