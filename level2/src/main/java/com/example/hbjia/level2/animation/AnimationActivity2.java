package com.example.hbjia.level2.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hbjia.level2.R;

public class AnimationActivity2 extends Activity {

    private static final String TAG = "AnimationActivity2";

    private int mScreenWidth;
    private int mScreenHeight;
    private DisplayMetrics dm = new DisplayMetrics();
    private Rect frame = new Rect();
    private int mActionBarHeight;
    private ImageView mBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_activity2);

        mBall = (ImageView) this.findViewById(R.id.id_ball);

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;


        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        mActionBarHeight = frame.top;

        Log.i(this.getClass().getName(), "Width = " + mScreenWidth + "Height == " + mScreenHeight + "mActionBarHeight == " + mActionBarHeight);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animation_activity2, menu);
        return true;
    }

    public void verticalRun(View view) {
        final ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
                                    - mBall.getHeight());
        animator.setTarget(mBall);
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBall.setTranslationY((Float) animator.getAnimatedValue());
            }
        });
    }

    public void paoWuXian(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF pointF, PointF pointF2) {
                Log.e(TAG, fraction * 3 + "");

                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                mBall.setX(point.x);
                mBall.setY(point.y);
            }
        });
    }

    public void deleteBall(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBall, "alpha", 1.0f, 0.5f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i(TAG, "onAnimationEnd");
                ViewGroup parent = (ViewGroup) mBall.getParent();
                if (parent != null) {
                    parent.removeView(mBall);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.i(TAG, "onAnimationRepeat");
            }
        });
        animator.start();
    }

    public void togetherRun(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBall, "scaleY",
                1.0f, 2.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
    }

    public void playWithAfter(View view) {
        float cx = mBall.getX();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBall, "scaleY",
                1.0f, 2.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mBall, "x", cx, 0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mBall, "x", cx);

        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1);
        animatorSet.play(animator2);
        animatorSet.play(animator3);
        animatorSet.play(animator4);
        animatorSet.setDuration(1000);
        animatorSet.start();

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
