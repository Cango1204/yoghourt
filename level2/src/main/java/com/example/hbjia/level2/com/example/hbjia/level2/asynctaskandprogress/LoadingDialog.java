package com.example.hbjia.level2.com.example.hbjia.level2.asynctaskandprogress;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hbjia.level2.R;

/**
 * Created by hbjia on 2015/1/26.
 */
public class LoadingDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading, null);
        TextView title = (TextView) view.findViewById(R.id.id_dialog_loading_msg);
        title.setText("Loading");
        Dialog dialog = new Dialog(getActivity(), R.style.dialog);
        dialog.setContentView(view);
        return dialog;
    }
}
