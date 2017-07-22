package com.dev21.customersupportdesk.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.fragments.ComplaintsFragment;
import com.dev21.customersupportdesk.fragments.FeedbackFragment;
import com.dev21.customersupportdesk.fragments.SuggestionsFragment;

public class CustomerDashboard extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private LinearLayout complaintsTab, suggestionsTab, feedbackTab;
    private FrameLayout container;
    private TextView fab;
    private TextView complaintsLabel, suggestionsLabel, feedbackLabel, screenTitle;

    private String selectedTab = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        container = (FrameLayout) findViewById(R.id.container);

        complaintsLabel = (TextView) findViewById(R.id.complaintsLabel);
        suggestionsLabel = (TextView) findViewById(R.id.suggestionsLabel);
        feedbackLabel = (TextView) findViewById(R.id.feedbackLabel);
        screenTitle = (TextView) findViewById(R.id.screenTitle);

        complaintsTab = (LinearLayout) findViewById(R.id.complaintsTab);
        suggestionsTab = (LinearLayout) findViewById(R.id.suggestionsTab);
        feedbackTab = (LinearLayout) findViewById(R.id.feedbackTab);

        fab = (TextView) findViewById(R.id.fab);

        complaintsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Complaints";
                highlightComplaintsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaints")).commit();
            }
        });

        suggestionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Suggestions";
                highlightSuggestionsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, SuggestionsFragment.newInstance("Suggestions")).commit();
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, AddCSFActivity.class);
                switch (selectedTab) {
                    case "Complaints":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                    case "Suggestions":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                    case "Feedback":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                }
            }
        });

        selectedTab = "Complaints";
        highlightComplaintsTab();
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaints")).commit();

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

}
