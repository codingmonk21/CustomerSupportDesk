package com.dev21.customersupportdesk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dev21.customersupportdesk.helper.FireBaseReferenceHelper;
import com.dev21.customersupportdesk.helper.ProgressDialogHelper;
import com.dev21.customersupportdesk.helper.ToastHelper;
import com.dev21.customersupportdesk.models.ComplaintBean;
import com.dev21.customersupportdesk.models.FeedbackBean;
import com.dev21.customersupportdesk.models.SuggestionBean;
import com.dev21.customersupportdesk.util.Config;
import com.dev21.customersupportdesk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UpdateCSFStatusActivity extends AppCompatActivity {
    private final String TAG = UpdateCSFStatusActivity.class.getSimpleName();

    private EditText messageEdt;
    private TextView buttonText,screenTitle;
    private LinearLayout updateButton;

    private ProgressDialog progressDialog;
    private ProgressDialogHelper progressDialogHelper;

    private FireBaseReferenceHelper fireBaseReferenceHelper;
    private DatabaseReference mainCSFRef, userCSFRef;

    private ComplaintBean complaintBean;
    private SuggestionBean suggestionBean;
    private FeedbackBean feedbackBean;

    private Spinner statusSpinner;
    private ArrayAdapter<String> statusAdapter;

    private String[] complaintStatus = new String[]{"Pending", "Solved"};
    private String[] suggestionStatus = new String[]{"Hold", "Accepted", "Considered", "Thank you"};
    private String[] feedbackStatus = new String[]{"Pending", "Acknowledged", "Thank you"};

    private String statusVal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_csf_status);

        Intent intent = getIntent();
        final String actionType = intent.getStringExtra("actionType");

        buttonText = (TextView)findViewById(R.id.buttonText);
        screenTitle = (TextView)findViewById(R.id.screenTitle);

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        messageEdt = (EditText) findViewById(R.id.messageEdt);
        updateButton = (LinearLayout) findViewById(R.id.updateButton);

        switch (actionType) {
            case "Complaint":
                screenTitle.setText("Update complaint status");
                complaintBean = (ComplaintBean) intent.getSerializableExtra("Complaint");
                break;

            case "Suggestion":
                screenTitle.setText("Update suggestion status");
                suggestionBean = (SuggestionBean) intent.getSerializableExtra("Suggestion");
                break;

            case "Feedback":
                screenTitle.setText("Update feedback status");
                feedbackBean = (FeedbackBean) intent.getSerializableExtra("Feedback");
                break;

            default:
                break;
        }

        progressDialogHelper = new ProgressDialogHelper(this, true, true);
        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(this);

        switch (actionType) {
            case "Complaint":
                statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, complaintStatus);
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusSpinner.setAdapter(statusAdapter);

                mainCSFRef = fireBaseReferenceHelper.complaints().child(complaintBean.getComplaintID());
                userCSFRef = fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_COMPLAINTS_NODE).child(complaintBean.getComplaintID());
                break;

            case "Suggestion":
                statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, suggestionStatus);
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusSpinner.setAdapter(statusAdapter);

                mainCSFRef = fireBaseReferenceHelper.suggestions().child(suggestionBean.getSuggestionID());
                userCSFRef = fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_SUGGESTIONS_NODE).child(suggestionBean.getSuggestionID());
                break;

            case "Feedback":
                statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, feedbackStatus);
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusSpinner.setAdapter(statusAdapter);

                mainCSFRef = fireBaseReferenceHelper.feedback().child(feedbackBean.getFeedbackID());
                userCSFRef = fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_FEEDBACK_NODE).child(feedbackBean.getFeedbackID());
                break;

            default:
                break;
        }

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusVal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEdt.getText().toString().trim();

                showDialog();

                switch (actionType) {
                    case "Complaint":
                        complaintBean.setComplaintStatus(statusVal);
                        complaintBean.setComplaintResponseMessage(message);
                        mainCSFRef.setValue(complaintBean);
                        userCSFRef.setValue(complaintBean);
                        break;

                    case "Suggestion":
                        suggestionBean.setSuggestionStatus(statusVal);
                        suggestionBean.setSuggestionResponseMessage(message);
                        mainCSFRef.setValue(suggestionBean);
                        userCSFRef.setValue(suggestionBean);
                        break;

                    case "Feedback":
                        feedbackBean.setFeedbackStatus(statusVal);
                        feedbackBean.setFeedbackResponseMessage(message);
                        mainCSFRef.setValue(feedbackBean);
                        userCSFRef.setValue(feedbackBean);
                        break;

                    default:
                        break;
                }

                mainCSFRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        userCSFRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                hideDialog();
                                finish();
                                ToastHelper.showToast(getApplicationContext(), "Status updated successfully");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                hideDialog();
                                ToastHelper.showToast(getApplicationContext(), "Couldn't update the details. Try again");
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Couldn't update the details. Try again");
                    }
                });
            }
        });
    }

    private void showDialog() {
        progressDialog.setMessage("Updating....");
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
