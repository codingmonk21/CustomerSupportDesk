package com.dev21.customersupportdesk.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Prajwal on 07/07/17.
 */

public class ToastHelper {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastAtCenter(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
