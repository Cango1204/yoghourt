package com.example.hbjia.level2.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.hbjia.level2.R;

/**
 * Created by hbjia on 2015/1/29.
 */
public class DeleteListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener{

    private GestureDetector gestureDetector;
    private View deleteButton;
    private ViewGroup itemLayout;
    private int selectedItem;
    private boolean isDeleteShown;
    private OnDeleteListener listener;

    public DeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    public void setOnDeleteListener(OnDeleteListener l){
        listener = l;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.i(this.getClass().getName(), "GestureDetector --- onDown");
        if(!isDeleteShown) {
            selectedItem = pointToPosition((int)event.getX(), (int)event.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event, MotionEvent event2, float v, float v2) {
        Log.i(this.getClass().getName(), "onScroll --- v = " + v + ", v2 = " + v2);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onFling(MotionEvent event, MotionEvent event2, float velocityX, float velocityY) {
        Log.i(this.getClass().getName(), "onFling --- velocityX = " + velocityX + ", velocityY = " + velocityY);
        if(!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {
            deleteButton = LayoutInflater.from(getContext()).inflate(R.layout.delete_button, null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemLayout.removeView(deleteButton);
                    deleteButton = null;
                    isDeleteShown = false;
                    listener.onDelete(selectedItem);
                }
            });
            Log.i(this.getClass().getName(), "onFling --- selectedItem = " + selectedItem + ", getFirstVisiblePosition = " + getFirstVisiblePosition());
            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton, layoutParams);
            isDeleteShown = true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.i(this.getClass().getName(), "OnTouchListener --- onTouch");
        if(isDeleteShown) {
            itemLayout.removeView(deleteButton);
            deleteButton = null;
            isDeleteShown = false;
            return false;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    public interface OnDeleteListener{
        void onDelete(int index);
    }
}
