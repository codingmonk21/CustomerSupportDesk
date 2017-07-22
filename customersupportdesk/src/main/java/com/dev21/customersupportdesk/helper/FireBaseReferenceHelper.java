package com.dev21.customersupportdesk.helper;

import android.content.Context;

import com.dev21.customersupportdesk.util.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Prajwal on 17/07/17.
 */

public class FireBaseReferenceHelper {
    private Context context;
    private DatabaseReference complaintsReference, suggestionsReference, feedbackReference,
            updateUserReference, allUsersReference;

    private SharedPreferenceHelper sharedPreferenceHelper;

    public FireBaseReferenceHelper(Context context) {
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context, Config.SP_ROOT_NAME);
    }

    public DatabaseReference complaints() {

        if (complaintsReference == null) {
            complaintsReference = FirebaseDatabase.getInstance().getReference(Config.COMPLAINTS_NODE);
        }

        return complaintsReference;
    }

    public DatabaseReference suggestions() {

        if (suggestionsReference == null) {
            suggestionsReference = FirebaseDatabase.getInstance().getReference(Config.SUGGESTIONS_NODE);
        }

        return suggestionsReference;
    }

    public DatabaseReference feedback() {

        if (feedbackReference == null) {
            feedbackReference = FirebaseDatabase.getInstance().getReference(Config.FEEDBACK_NODE);
        }

        return feedbackReference;
    }

    public DatabaseReference userSpecificCSFReference() {

        if (updateUserReference == null) {
            updateUserReference = FirebaseDatabase.getInstance().getReference(Config.USER_SPECIFIC_CSF_NODE).child(sharedPreferenceHelper.getString(Config.SP_USER_ID, ""));
        }

        return updateUserReference;
    }

    public DatabaseReference allUsersReference() {

        if (allUsersReference == null) {
            allUsersReference = FirebaseDatabase.getInstance().getReference(Config.ALL_USERS_NODE);
        }

        return allUsersReference;
    }

}
