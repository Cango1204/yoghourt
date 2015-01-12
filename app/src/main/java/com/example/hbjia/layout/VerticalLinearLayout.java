package com.example.hbjia.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.ViewGroup;
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

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {

    }
}
