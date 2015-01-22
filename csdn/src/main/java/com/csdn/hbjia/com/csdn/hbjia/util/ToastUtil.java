package com.csdn.hbjia.com.csdn.hbjia.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hbjia on 2015/1/22.
 */
public class ToastUtil {
    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
