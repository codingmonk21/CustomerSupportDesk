package com.dev21.customersupportdesk.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.fragments.ComplaintsFragment;
import com.dev21.customersupportdesk.fragments.FeedbackFragment;
import com.dev21.customersupportdesk.fragments.SuggestionsFragment;
import com.dev21.customersupportdesk.helper.ProgressDialogHelper;
import com.dev21.customersupportdesk.helper.SharedPreferenceHelper;
import com.dev21.customersupportdesk.util.Config;

public class AdminDashboard extends AppCompatActivity {
    private final String TAG = AdminDashboard.class.getSimpleName();

    private LinearLayout complaintsTab, suggestionsTab, feedbackTab;
    private TextView complaintsLabel, suggestionsLabel, feedbackLabel, screenTitle;

    private ProgressDialogHelper progressDialogHelper;
    private ProgressDialog progressDialog;

    private SharedPreferenceHelper sharedPreferenceHelper;

    public static String selectedTab;
    private Dialog logOutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        progressDialogHelper = new ProgressDialogHelper(this, true, true);
        progressDialog = progressDialogHelper.getProgressDialog();
        progressDialog.setMessage("Loading...");

        complaintsTab = (LinearLayout) findViewById(R.id.complaintsTab);
        suggestionsTab = (LinearLayout) findViewById(R.id.suggestionsTab);
        feedbackTab = (LinearLayout) findViewById(R.id.feedbackTab);

        complaintsLabel = (TextView) findViewById(R.id.complaintsLabel);
        suggestionsLabel = (TextView) findViewById(R.id.suggestionsLabel);
        feedbackLabel = (TextView) findViewById(R.id.feedbackLabel);
        screenTitle = (TextView) findViewById(R.id.screenTitle);

        complaintsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Complaints";
                highlightComplaintsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaint")).commit();
            }
        });

        suggestionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Suggestions";
                highlightSuggestionsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, SuggestionsFragment.newInstance("Suggestion")).commit();
            }
        });

        feedbackTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Feedback";
                highlightFeedbackTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FeedbackFragment.newInstance("Feedback")).commit();
            }
        });

        selectedTab = "Complaints";
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaint")).commit();
        highlightComplaintsTab();
    }

    private void highlightComplaintsTab() {
        complaintsLabel.setTextColor(Color.parseColor("#01579b"));
        suggestionsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        feedbackLabel.setTextColor(Color.parseColor("#FFFFFF"));

        complaintsTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        suggestionsTab.setBackgroundColor(Color.parseColor("#01579b"));
        feedbackTab.setBackgroundColor(Color.parseColor("#01579b"));
    }

    private void highlightSuggestionsTab() {
        complaintsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        suggestionsLabel.setTextColor(Color.parseColor("#01579b"));
        feedbackLabel.setTextColor(Color.parseColor("#FFFFFF"));

        complaintsTab.setBackgroundColor(Color.parseColor("#01579b"));
        suggestionsTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        feedbackTab.setBackgroundColor(Color.parseColor("#01579b"));
    }

    private void highlightFeedbackTab() {
        complaintsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        suggestionsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        feedbackLabel.setTextColor(Color.parseColor("#01579b"));

        complaintsTab.setBackgroundColor(Color.parseColor("#01579b"));
        suggestionsTab.setBackgroundColor(Color.parseColor("#01579b"));
        feedbackTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showLogoutPopUp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showLogoutPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOutDialog.dismiss();
            }
        });

        logOutDialog = builder.create();
        logOutDialog.setCancelable(false);
        logOutDialog.setCanceledOnTouchOutside(false);
        logOutDialog.show();
    }
}
