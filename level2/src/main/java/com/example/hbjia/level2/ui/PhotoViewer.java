package com.example.hbjia.level2.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
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
        center();
        imageView.setImageMatrix(matrix);
    }

    private void center() {
        center(true, true);
    }

    private void center(boolean horizontal, boolean vertical) {
        Matrix mat = new Matrix();
        mat.set(matrix); //Deep copy
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        mat.mapRect(rect);

        float height = rect.height();
        float width = rect.width();
        float deltaX = 0, deltaY = 0;

        // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
        if(vertical) {
            int screenHeight = dm.heightPixels;
            if(height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY =- rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = imageView.getHeight() - rect.bottom;
            }
        }

        if(horizontal) {
            int screenWidth = dm.widthPixels;
            if(width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX =- rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.left;
            }
        }

        matrix.postTranslate(deltaX, deltaY);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

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
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //主点按下
            case MotionEvent.ACTION_DOWN :
                savedMatrix.set(matrix);
                prev.set(motionEvent.getX(), motionEvent.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN :
                dist = spacing(motionEvent);
                if(spacing(motionEvent) > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, motionEvent);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP :
            case MotionEvent.ACTION_POINTER_UP :
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE :
                if(mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(motionEvent.getX() - prev.x,
                            motionEvent.getY() - prev.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(motionEvent);
                    if(newDist > 10f) {
                        matrix.set(savedMatrix);
                        float tScale = newDist / dist;
                        matrix.postScale(tScale, tScale, mid.x, mid.y);
                    }
                }
                break;
        }
        imageView.setImageMatrix(matrix);
        checkView();
        return true;
    }

    private void checkView() {
        float p[] = new float[9];
        matrix.getValues(p);
        if(mode == ZOOM) {
            if(p[0] < MIN_SCALE) {
                matrix.setScale(MIN_SCALE, MIN_SCALE);
            }
            if(p[0] > MAX_SCALE) {
                matrix.setScale(MAX_SCALE, MAX_SCALE);
            }
        }
        center();
    }
}
