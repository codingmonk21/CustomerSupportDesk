package com.dev21.customersupportdesk.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.dev21.customersupportdesk.interfaces.UserRegistrationListener;
import com.dev21.customersupportdesk.models.UserDetail;
import com.dev21.customersupportdesk.util.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Prajwal on 17/07/17.
 */

public class UserRegistration {
    private FireBaseReferenceHelper fireBaseReferenceHelper;
    private SharedPreferenceHelper sharedPreferenceHelper;

    private Context context;
    private UserRegistrationListener userRegistrationListener;

    public UserRegistration(Context context) {
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context, Config.SP_ROOT_NAME);
        fireBaseReferenceHelper = new FireBaseReferenceHelper(context);
    }

    public void isUserRegistered(final String uniqueID) {

        if (sharedPreferenceHelper.getSharedPreference().getBoolean(Config.SP_USER_DETAILS_STATUS, false)) {
            if (userRegistrationListener != null) {
                userRegistrationListener.registrationStatus(true);
            }
        } else {
            fireBaseReferenceHelper.allUsersReference().child(uniqueID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        UserDetail userDetail = dataSnapshot.getValue(UserDetail.class);

                        sharedPreferenceHelper.putString(Config.SP_USER_ID, uniqueID);
                        sharedPreferenceHelper.putString(Config.SP_USER_NAME, userDetail.getUserName());
                        sharedPreferenceHelper.putString(Config.SP_USER_EMAIL, userDetail.getUserEmailID());
                        sharedPreferenceHelper.putString(Config.SP_USER_MOBILE, userDetail.getUserMobileNum());
                        sharedPreferenceHelper.putBoolean(Config.SP_USER_DETAILS_STATUS, true);

                        if (userRegistrationListener != null) {
                            userRegistrationListener.registrationStatus(true);
                        }

                    } else {
                        sharedPreferenceHelper.putString(Config.SP_USER_ID, uniqueID);
                        userRegistrationListener.registrationStatus(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void setUserRegistrationListener(UserRegistrationListener userRegistrationListener) {
        this.userRegistrationListener = userRegistrationListener;
    }
}
