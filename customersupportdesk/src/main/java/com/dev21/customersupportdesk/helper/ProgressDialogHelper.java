package com.dev21.customersupportdesk.helper;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Prajwal on 17/07/17.
 */

public class ProgressDialogHelper {
    private ProgressDialog progressDialog;

    public ProgressDialogHelper(Context context, boolean isCancelable, boolean isCancelledOnTouchOutside) {
        progressDialog = new ProgressDialog(context);
        if (isCancelable) {
            progressDialog.setCancelable(false);
        }

        if (isCancelledOnTouchOutside) {
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }
}
