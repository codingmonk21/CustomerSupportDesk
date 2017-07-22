package com.dev21.customersupportdesk.helper;

import android.os.Build;

/**
 * Created by Prajwal on 07/07/17.
 */

public class OSVersion {
    public static boolean isMarshmallowAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNoughat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }
}
