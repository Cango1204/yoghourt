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
import android.widget.Toast;

import com.example.hbjia.dialogfragment.EvaluteDialog;
import com.example.hbjia.http.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private String mArgument;
    public static final String ARGUMENT = "argument";
    public static final String RESPONSE = "response";
    public static final int REQUEST_EVALUATE = 0x112;

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
//            Intent intent = new Intent();
//            intent.putExtra(RESPONSE, "good");
//            getActivity().setResult(ListTitleFragment.RESULT_CODE, intent);
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
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
            , ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setText(mArgument);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.argb(random.nextInt(100), random.nextInt(255),
                random.nextInt(255), random.nextInt(255)));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluteDialog dialog = new EvaluteDialog();
                dialog.setTargetFragment(ContentFragment.this, REQUEST_EVALUATE);
                dialog.show(getFragmentManager(), "evaluate_dialog");
            }
        });
        return tv;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EVALUATE) {
            String evaluate = data.getStringExtra(EvaluteDialog.RESPONSE_EVALUATE);
            Toast.makeText(getActivity(), evaluate, Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra(RESPONSE, evaluate);
            getActivity().setResult(ListTitleFragment.RESULT_CODE, intent);
        }
    }
}
