package com.example.hbjia.level2.asynctaskandprogress;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * Created by hbjia on 2015/1/26.
 */
public class RetainedFragment extends Fragment{
    private Bitmap data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(Bitmap data) {
        this.data = data;
    }

    public Bitmap getData() {
        return data;
    }
}
