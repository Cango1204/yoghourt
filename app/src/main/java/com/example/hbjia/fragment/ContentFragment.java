package com.example.hbjia.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hbjia.http.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private String mArgument;
    public static final String ARGUMENT = "argument";
    public static final String RESPONSE = "response";

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
            Intent intent = new Intent();
            intent.putExtra(RESPONSE, "good");
            getActivity().setResult(ListTitleFragment.RESULT_CODE, intent);
        }
    }

    public static ContentFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random random = new Random();
        TextView tv = new TextView(getActivity());
        tv.setText(mArgument);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.argb(random.nextInt(100), random.nextInt(255),
                random.nextInt(255), random.nextInt(255)));
        return tv;
    }


}
