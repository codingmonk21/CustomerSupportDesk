package com.dev21.customersupportdesk.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev21.customersupportdesk.helper.FireBaseReferenceHelper;
import com.dev21.customersupportdesk.helper.FireBaseStorageHelper;
import com.dev21.customersupportdesk.helper.NetworkHelper;
import com.dev21.customersupportdesk.helper.OSVersion;
import com.dev21.customersupportdesk.helper.ProgressDialogHelper;
import com.dev21.customersupportdesk.helper.SharedPreferenceHelper;
import com.dev21.customersupportdesk.helper.ToastHelper;
import com.dev21.customersupportdesk.helper.UniqueIDGenerator;
import com.dev21.customersupportdesk.interfaces.ImageUploadListener;
import com.dev21.customersupportdesk.models.ComplaintBean;
import com.dev21.customersupportdesk.models.FeedbackBean;
import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.models.SuggestionBean;
import com.dev21.customersupportdesk.util.Config;
import com.dev21.customersupportdesk.util.TimeStamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class AddCSFActivity extends AppCompatActivity implements ImageUploadListener {
    private final String TAG = getClass().getSimpleName();

    private RelativeLayout actionButton;
    private EditText contentTitle, contentDesc;
    private ImageView feedImage;
    private TextView actionText, screenTitle, addImageLabel;

    private String action = "";

    private FireBaseReferenceHelper fireBaseReferenceHelper;
    private FireBaseStorageHelper fireBaseStorageHelper;

    private ProgressDialogHelper progressDialogHelper;
    private ProgressDialog progressDialog;

    private SharedPreferenceHelper sharedPreferenceHelper;

    private Uri imageURi = null;

    private String[] requiredPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private ArrayList<String> permissionsToEnable = new ArrayList<>();
    private ArrayList<String> userClickedNeverAskAgainPermissions = new ArrayList<>();
    private ArrayList<String> userDeniedPermissions = new ArrayList<>();
    private final int REQUEST_PERMISSION = 200;
    private int permissionCount = 0;
    private int permissionCallCount;
    private boolean requestToEnablePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        Intent intent = getIntent();
        action = intent.getStringExtra("selectedTab");

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        progressDialogHelper = new ProgressDialogHelper(this, true, true);
        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(this);
        fireBaseStorageHelper = new FireBaseStorageHelper();

        actionText = (TextView) findViewById(R.id.actionText);
        screenTitle = (TextView) findViewById(R.id.screenTitle);
        addImageLabel = (TextView) findViewById(R.id.addImageLabel);

        actionButton = (RelativeLayout) findViewById(R.id.actionButton);

        contentTitle = (EditText) findViewById(R.id.genericTitleEdt);
        contentDesc = (EditText) findViewById(R.id.genericDescriptionEdt);

        feedImage = (ImageView) findViewById(R.id.feedImage);

        feedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OSVersion.isMarshmallowAndAbove()) {
                    checkForPermissions();
                } else {
                    selectImageAndCrop();
                }
            }
        });

        switch (action) {
            case "Complaints":
                actionText.setText("Send complaint");
                screenTitle.setText("Post complaint");
                break;
            case "Suggestions":
                actionText.setText("Send suggestion");
                screenTitle.setText("Post suggestion");
                break;
            case "Feedback":
                actionText.setText("Send feedback");
                screenTitle.setText("Post feedback");
                break;

            default:
                break;
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (action) {
                    case "Complaints":

                        if (NetworkHelper.isNetworkAvailable(AddCSFActivity.this)) {
                            if (contentTitle.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Title cannot be empty");
                                return;
                            }

                            if (contentDesc.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Description cannot be empty");
                                return;
                            }

                            if (imageURi != null) {
                                showDialog();
                                fireBaseStorageHelper.setImageUploadListener(AddCSFActivity.this);
                                fireBaseStorageHelper.uploadImageToStorage(imageURi, "Complaints");
                            } else {
                                sendComplaint("");
                            }
                        } else {
                            ToastHelper.showToast(getApplicationContext(), "Check your internet connection");
                        }

                        break;

                    case "Suggestions":

                        if (NetworkHelper.isNetworkAvailable(AddCSFActivity.this)) {
                            if (contentTitle.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Title cannot be empty");
                                return;
                            }

                            if (contentDesc.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Description cannot be empty");
                                return;
                            }

                            if (imageURi != null) {
                                showDialog();
                                fireBaseStorageHelper.setImageUploadListener(AddCSFActivity.this);
                                fireBaseStorageHelper.uploadImageToStorage(imageURi, "Suggestions");
                            } else {
                                sendSuggestion("");
                            }
                        } else {
                            ToastHelper.showToast(getApplicationContext(), "Check your internet connection");
                        }

                        break;

                    case "Feedback":
                        if (NetworkHelper.isNetworkAvailable(AddCSFActivity.this)) {
                            if (contentTitle.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Title cannot be empty");
                                return;
                            }

                            if (contentDesc.getText().toString().trim().length() == 0) {
                                ToastHelper.showToast(getApplicationContext(), "Description cannot be empty");
                                return;
                            }

                            if (imageURi != null) {
                                showDialog();
                                fireBaseStorageHelper.setImageUploadListener(AddCSFActivity.this);
                                fireBaseStorageHelper.uploadImageToStorage(imageURi, "Feedback");
                            } else {
                                sendFeedback("");
                            }
                        } else {
                            ToastHelper.showToast(getApplicationContext(), "Check your internet connection");
                        }

                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void sendComplaint(String imageURL) {
        ComplaintBean cb = new ComplaintBean();
        cb.setComplaintTitle(contentTitle.getText().toString());
        cb.setComplaintDescription(contentDesc.getText().toString().trim());
        cb.setComplaintImage(imageURL);
        cb.setComplaintID(UniqueIDGenerator.getUniqueID());

        cb.setUserID(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, ""));
        cb.setComplaintFrom(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_NAME, ""));
        cb.setCustomerMobNumber(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_MOBILE, ""));
        cb.setComplaintStatus("Pending");
        cb.setComplaintTimeStamp(TimeStamp.getCurrentTimeStamp());

        fireBaseReferenceHelper.complaints().child(cb.getComplaintID()).setValue(cb);
        fireBaseReferenceHelper.complaints().child(cb.getComplaintID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ComplaintBean cb = dataSnapshot.getValue(ComplaintBean.class);
                Log.e("Complaints Update", "" + dataSnapshot.getValue(ComplaintBean.class).getComplaintID());

                fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_COMPLAINTS_NODE).child(cb.getComplaintID()).setValue(cb);

                fireBaseReferenceHelper.userSpecificCSFReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Complaint sent successfully");
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Couldn't send the complaint. Please try again");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getApplicationContext(), "Couldn't send the complaint. Please try again");
            }
        });
    }

    private void sendSuggestion(String imageURL) {
        SuggestionBean sb = new SuggestionBean();
        sb.setSuggestionTitle(contentTitle.getText().toString());
        sb.setSuggestionDescription(contentDesc.getText().toString().trim());
        sb.setSuggestionImage(imageURL);
        sb.setSuggestionID(UniqueIDGenerator.getUniqueID());

        sb.setUserID(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, ""));
        sb.setSuggestionFrom(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_NAME, ""));
        sb.setCustomerMobNumber(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_MOBILE, ""));
        sb.setSuggestionStatus("Hold");
        sb.setSuggestionTimeStamp(TimeStamp.getCurrentTimeStamp());

        showDialog();
        fireBaseReferenceHelper.suggestions().child(sb.getSuggestionID()).setValue(sb);
        fireBaseReferenceHelper.suggestions().child(sb.getSuggestionID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SuggestionBean sb = dataSnapshot.getValue(SuggestionBean.class);
                Log.e("Suggestion Update", "" + dataSnapshot.getValue(SuggestionBean.class).getSuggestionID());

                fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_SUGGESTIONS_NODE).child(sb.getSuggestionID()).setValue(sb);

                fireBaseReferenceHelper.userSpecificCSFReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Suggestion sent successfully");
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Couldn't send the suggestion. Please try again");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getApplicationContext(), "Couldn't send the suggestion. Please try again");
            }
        });
    }

    private void sendFeedback(String imageURL) {
        FeedbackBean fb = new FeedbackBean();
        fb.setFeedbackTitle(contentTitle.getText().toString());
        fb.setFeedbackDescription(contentDesc.getText().toString().trim());
        fb.setFeedbackImage(imageURL);
        fb.setFeedbackID(UniqueIDGenerator.getUniqueID());

        fb.setUserID(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, ""));
        fb.setFeedbackFrom(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_NAME, ""));
        fb.setCustomerMobNumber(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_MOBILE, ""));
        fb.setFeedbackStatus("Pending");
        fb.setFeedbackTimestamp(TimeStamp.getCurrentTimeStamp());

        fireBaseReferenceHelper.feedback().child(fb.getFeedbackID()).setValue(fb);
        fireBaseReferenceHelper.feedback().child(fb.getFeedbackID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FeedbackBean fb = dataSnapshot.getValue(FeedbackBean.class);
                Log.e("Feedback Update", "" + dataSnapshot.getValue(FeedbackBean.class).getFeedbackID());

                fireBaseReferenceHelper.userSpecificCSFReference().child(Config.USER_FEEDBACK_NODE).child(fb.getFeedbackID()).setValue(fb);

                fireBaseReferenceHelper.userSpecificCSFReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Feedback sent successfully");
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideDialog();
                        ToastHelper.showToast(getApplicationContext(), "Couldn't send the feedback. Please try again");
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getApplicationContext(), "Couldn't send the feedback. Please try again");
            }
        });
    }

    private void selectImageAndCrop() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                addImageLabel.setVisibility(View.GONE);
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                imageURi = resultUri;
                if (resultUri != null) {
                    feedImage.setImageURI(resultUri);
                }
            }
        }
    }

    private void checkForPermissions() {

        permissionCallCount++;

        for (String requiredPermission : requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, requiredPermission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToEnable.add(requiredPermission);
            } else {
                permissionCount++;
            }
        }

        if (permissionsToEnable.size() > 0) {

            if (permissionCallCount == 1) {
                String[] temp = new String[permissionsToEnable.size()];
                ActivityCompat.requestPermissions(this, permissionsToEnable.toArray(temp), REQUEST_PERMISSION);
            } else {
                for (int i = 0; i < permissionsToEnable.size(); i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsToEnable.get(i))) {
                        userDeniedPermissions.add(permissionsToEnable.get(i));
                    } else {
                        userClickedNeverAskAgainPermissions.add(permissionsToEnable.get(i));
                    }
                }
            }

        } else {
            resetPermissionSetup();
            selectImageAndCrop();
        }

        if (userDeniedPermissions.size() > 0) {
            String[] temp = new String[userDeniedPermissions.size()];
            ActivityCompat.requestPermissions(this, userDeniedPermissions.toArray(temp), REQUEST_PERMISSION);
        } else if (userClickedNeverAskAgainPermissions.size() > 0) {
            resetPermissionSetup();
            showDialogToNavigateToAppSettingsPage("Permissions needs to be granted to proceed. Would you like to grant it now?");
        }
    }

    private void showDialogToNavigateToAppSettingsPage(String message) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestToEnablePermission = true;
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                ToastHelper.showToastAtCenter(AddCSFActivity.this, "Click on permissions");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestToEnablePermission = false;
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void resetPermissionSetup() {
        permissionCount = 0;
        permissionsToEnable.clear();
        userDeniedPermissions.clear();
        userClickedNeverAskAgainPermissions.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestToEnablePermission) {
            requestToEnablePermission = false;
            checkForPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        permissionCount++;
                    }
                }

                if (permissionCount != requiredPermissions.length) {
                    resetPermissionSetup();
                    ToastHelper.showToast(this, "Please grant the required permissions to proceed");
                } else {
                    selectImageAndCrop();
                }
            }
        }
    }

    private void showDialog() {
        progressDialog.setMessage("Sending....");
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onUploaded(Uri imageURI) {
        if (imageURI == null) {
            hideDialog();
            ToastHelper.showToast(getApplicationContext(), "Couldn't upload the image. Please try again");
        } else {
            switch (action) {
                case "Complaints":
                    sendComplaint(imageURI.toString());
                    break;
                case "Suggestions":
                    sendSuggestion(imageURI.toString());
                    break;
                case "Feedback":
                    sendFeedback(imageURI.toString());
                    break;
            }
        }
    }
}
