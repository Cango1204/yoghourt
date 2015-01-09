package com.example.hbjia.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hbjia on 2015/1/8.
 */
public class NewViewHolder {
    private SparseArray<View> mViews;
    private static View mContentView;

    private NewViewHolder(Context context, ViewGroup parent, int layoutId, int postion){
        this.mViews = new SparseArray<View>();
        mContentView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mContentView.setTag(this);
    }

    public static NewViewHolder get(Context context, View contentView, ViewGroup parent, int layoutId, int postion) {
        if(contentView == null) {
            return new NewViewHolder(context, parent, layoutId, postion);
        } else {
            return (NewViewHolder) mContentView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if(view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getContentView(){
        return mContentView;
    }
}
