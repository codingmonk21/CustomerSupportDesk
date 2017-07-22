package com.dev21.customersupportdesk.util;

import android.util.Log;

/**
 * Created by Prajwal on 07/07/17.
 */

public class Logger {
    public static void d(String TAG, String e) {
        Log.d(TAG, e);
    }

    public static void e(String TAG, String e) {
        Log.e(TAG, e);
    }

    public static void i(String TAG, String e) {
        Log.i(TAG, e);
    }
}
