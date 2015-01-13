package com.example.hbjia.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by hbjia on 2015/1/12.
 */
public class VerticalLinearLayout extends ViewGroup{

    private int mScreenHeight;
    private int mScrollStart;
    private int mScrollEnd;
    private int mLastY;
    private Scroller mScroller;
    private boolean isScrolling;
    private VelocityTracker mVelocityTracker;
    private int currentPage = 0;

    private OnPageChangeListener mOnPageChangeListener;

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {
            int childCount = getChildCount();
            MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
            layoutParams.height = mScreenHeight * childCount;
            setLayoutParams(layoutParams);

            for(int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if(child.getVisibility() != GONE) {
                    child.layout(l, i*mScreenHeight, r, (i+1)*mScreenHeight);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for(int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void obtainVelocity(MotionEvent event) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private boolean wantScrollToNext() {
        return mScrollEnd > mScrollStart;
    }

    private int getVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }

    private boolean shouldScrollToNext() {
        return mScrollEnd - mScrollStart > mScreenHeight / 2
                || Math.abs(getVelocity()) > 600;
    }

    private boolean wantScrollToPre() {
        return mScrollEnd < mScrollStart;
    }

    private boolean shouldScrollToPre() {
        return -mScrollEnd + mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    private void recycleVelocity() {
        if (mVelocityTracker != null)
        {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isScrolling) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        int y = (int) event.getY();

        obtainVelocity(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mScrollStart = getScrollY();
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                int scrollY = getScrollY();
                if(dy < 0 && scrollY + dy < 0) {
                    dy = -scrollY;
                }
                if(dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
                    dy = getHeight() - mScreenHeight - scrollY;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                mScrollEnd = getScrollY();
                int dScrollY = mScrollEnd - mScrollStart;
                if(wantScrollToNext()) {
                    if(shouldScrollToNext()) {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                if (wantScrollToPre())// 往下滑动
                {
                    if (shouldScrollToPre())
                    {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);

                    } else
                    {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                isScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        } else
        {

            int position = getScrollY() / mScreenHeight;

            Log.e("xxx", position + "," + currentPage);
            if (position != currentPage)
            {
                if (mOnPageChangeListener != null)
                {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);
                }
            }

            isScrolling = false;
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public interface OnPageChangeListener {
        void onPageChange(int currentPage);
    }
}
