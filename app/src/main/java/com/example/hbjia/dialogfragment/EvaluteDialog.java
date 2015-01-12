package com.example.hbjia.dialogfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.hbjia.fragment.ContentFragment;

/**
 * Created by hbjia on 2015/1/12.
 */
public class EvaluteDialog extends DialogFragment{

    private String[] mEvaluaeVals = new String[]{"GOOD", "BAD", "NORMAL"};
    public static final String RESPONSE_EVALUATE = "response_evaluate";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Evaluate :").setItems(mEvaluaeVals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setResult(i);
            }
        });
        return builder.create();
    }

    protected void setResult(int which) {
        if(getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(RESPONSE_EVALUATE, mEvaluaeVals[which]);
        getTargetFragment().onActivityResult(ContentFragment.REQUEST_EVALUATE, Activity.RESULT_OK, intent);
    }
}
