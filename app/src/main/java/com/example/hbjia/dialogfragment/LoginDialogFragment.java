package com.example.hbjia.dialogfragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hbjia.http.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginDialogFragment extends DialogFragment {

    private EditText mUserName;
    private EditText mPassword;

    public interface LoginInputListener{
        public void onLoginInputComplete(String username, String password);
    }

    public LoginDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);
        mUserName = (EditText) view.findViewById(R.id.id_txt_username);
        mPassword = (EditText) view.findViewById(R.id.id_txt_password);
        builder.setView(view)
                .setPositiveButton("Sign in",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoginInputListener listener = (LoginInputListener)getActivity();
                                listener.onLoginInputComplete(mUserName.getText().toString(),
                                        mPassword.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel", null);
        return builder.create();
    }
}
