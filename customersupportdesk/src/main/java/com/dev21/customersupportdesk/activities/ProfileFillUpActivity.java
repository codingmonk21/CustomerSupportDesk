package com.dev21.customersupportdesk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev21.customersupportdesk.helper.FireBaseReferenceHelper;
import com.dev21.customersupportdesk.helper.ProgressDialogHelper;
import com.dev21.customersupportdesk.helper.SharedPreferenceHelper;
import com.dev21.customersupportdesk.helper.ToastHelper;
import com.dev21.customersupportdesk.util.Config;
import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.util.TimeStamp;
import com.dev21.customersupportdesk.models.UserDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileFillUpActivity extends AppCompatActivity {
    private final String TAG = ProfileFillUpActivity.class.getSimpleName();

    private TextView screenTitle;
    private EditText nameEdt, emailEdt, mobileEdt;
    private SharedPreferenceHelper sharedPreferenceHelper;

    private String userID;
    private UserDetail userDetail;

    private LinearLayout saveButton;
    private ProgressDialog progressDialog;
    private ProgressDialogHelper progressDialogHelper;

    private FireBaseReferenceHelper fireBaseReferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialogHelper = new ProgressDialogHelper(this, true, true);
        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(this);

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        userID = sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, null);

        nameEdt = (EditText) findViewById(R.id.nameEdt);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        mobileEdt = (EditText) findViewById(R.id.mobileEdt);

        saveButton = (LinearLayout) findViewById(R.id.saveButton);

        screenTitle = (TextView) findViewById(R.id.screenTitle);
        screenTitle.setText("Profile Details");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdt.getText().toString().trim();
                String emailID = emailEdt.getText().toString().trim();
                String mobileNum = mobileEdt.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    ToastHelper.showToast(getApplicationContext(), "Name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(emailID)) {
                    ToastHelper.showToast(getApplicationContext(), "Email ID cannot be empty");
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText()).matches()) {
                    ToastHelper.showToast(getApplicationContext(), "Enter valid Email ID");
                }

                if (TextUtils.isEmpty(mobileNum)) {
                    ToastHelper.showToast(getApplicationContext(), "Mobile number cannot be empty");
                    return;
                }

                userDetail = new UserDetail();
                userDetail.setUserName(name);
                userDetail.setUserEmailID(emailID);
                userDetail.setUserMobileNum(mobileNum);
                userDetail.setCreatedAt(TimeStamp.getCurrentTimeStamp());
                userDetail.setProfileCompleted("Y");

                if (userID != null && !userID.trim().equals("")) {
                    addUser();
                } else {
                    ToastHelper.showToast(getApplicationContext(), "Invalid unique ID");
                }

            }
        });
    }

    private void addUser() {
        showDialog();
        fireBaseReferenceHelper.allUsersReference().child(userID).setValue(userDetail);
        fireBaseReferenceHelper.allUsersReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideDialog();
                if (dataSnapshot != null) {
                    UserDetail userDetail = dataSnapshot.child(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, "")).getValue(UserDetail.class);

                    Log.e(TAG, "Username : " + userDetail.getUserName());
                    Log.e(TAG, "Email : " + userDetail.getUserEmailID());
                    Log.e(TAG, "Mobile : " + userDetail.getUserMobileNum());

                    sharedPreferenceHelper.putString(Config.SP_USER_NAME, userDetail.getUserName());
                    sharedPreferenceHelper.putString(Config.SP_USER_EMAIL, userDetail.getUserEmailID());
                    sharedPreferenceHelper.putString(Config.SP_USER_MOBILE, userDetail.getUserMobileNum());
                    sharedPreferenceHelper.putBoolean(Config.SP_USER_DETAILS_STATUS, true);

                    startActivity(new Intent(ProfileFillUpActivity.this, CustomerDashboard.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getApplicationContext(), "Couldn't save the profile details. Please try again");
            }
        });
    }

    private void showDialog() {
        progressDialog.setMessage("Saving...");
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}