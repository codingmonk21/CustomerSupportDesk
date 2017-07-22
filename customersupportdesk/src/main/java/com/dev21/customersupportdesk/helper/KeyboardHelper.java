package com.dev21.customersupportdesk.helper;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by Prajwal on 07/07/17.
 */

public class KeyboardHelper {

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static void openKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
