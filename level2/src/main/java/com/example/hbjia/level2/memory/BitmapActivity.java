package com.example.hbjia.level2.memory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.hbjia.level2.R;

public class BitmapActivity extends Activity {

    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mImageView = (ImageView) this.findViewById(R.id.id_big_image);
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gai);
//        mImageView.setImageBitmap(mBitmap);

        mBitmap = compressImage(150, 150);
        mImageView.setImageBitmap(mBitmap);

//        BitmapFactory.Options options = new BitmapFactory.Options();
    }

    private Bitmap compressImage(int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.gai, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Log.i(this.getClass().getName(), "height = " + imageHeight + " width = " + imageWidth + " imageType = " + imageType);
        options.inSampleSize = caculateInSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), R.drawable.gai, options);
    }

    private int caculateInSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;
        if(width > reqWidth || height > reqHeight) {
            final int widthRatio = Math.round((float)width / (float)reqWidth);
            final int heghtRatio = Math.round((float)height / (float)reqHeight);
            inSampleSize = widthRatio < heghtRatio ? widthRatio : heghtRatio;
        }
        Log.i(this.getClass().getName(), "inSampleSize = " + inSampleSize);
        return inSampleSize;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bitmap, menu);
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
    protected void onDestroy() {
        mBitmap.recycle();
        super.onDestroy();
    }
}
