package com.example.hbjia.level2.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.hbjia.level2.R;

public class PhotoViewer extends Activity implements View.OnTouchListener{

    private static final String TAG = "PhotoViewer";
    public static final int RESULT_CODE_NOTFOUND = 404;

    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private DisplayMetrics dm;
    private ImageView imageView;
    private Bitmap bitmap;

    //最小缩放比例
    private static final float MIN_SCALE = 1.0f;
    //最大缩放比例
    private static final float MAX_SCALE = 10f;
    //图片的状态
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode = NONE;
    //第一个手指
    private PointF prev = new PointF();
    //第二个手指
    private PointF mid = new PointF();
    //初始的两个手指按下的触摸点的距离
    private float dist = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meinv);
        imageView = (ImageView) findViewById(R.id.id_imageView);
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

//    private void center(boolean )

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_viewer, menu);
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
