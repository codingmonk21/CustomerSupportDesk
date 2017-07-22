package com.dev21.customersupportdesk.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Prajwal on 10/04/17.
 */

public class NetworkHelper {

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else if (networkInfo == null) {
                return false;
            }
        }
        return false;
    }
}
