package com.example.hbjia.level2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by hbjia on 2015/3/26.
 */
public class SlideCutListView extends ListView {

    /**
     * 当前滑动的ListView position
     */
    private int slidePosition;
    /**
     * 手指按下的X的坐标
     */
    private int downX;
    /**
     * 手指按下的y的坐标
     * @param context
     */
    private int downY;
    /**
     * 屏幕宽度
     */
    private int screedWidth;

    /**
     * ListView的item
     */
    private View itemView;

    private Scroller scroller;
    private static final int SNAP_VELOCITY = 600;

    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;
    /**
     * 是否响应滑动，默认为不响应
     */
    private boolean isSlide = false;


    public SlideCutListView(Context context) {
        this(context, null);
    }

    public SlideCutListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
}
