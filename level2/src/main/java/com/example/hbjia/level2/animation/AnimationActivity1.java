package com.example.hbjia.level2.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hbjia.level2.R;

public class AnimationActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_activity1);
    }

    public void rotateAnimRun(final View v) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "alpha", 1.0F, 0.0F, 1.0F)
                .setDuration(500);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cVal = (Float)valueAnimator.getAnimatedValue();
                v.setAlpha(cVal);
                v.setScaleX(cVal);
                v.setScaleY(cVal);
            }
        });
    }

    public void propertyValuesHolder(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY).setDuration(1000).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animation_activity1, menu);
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
