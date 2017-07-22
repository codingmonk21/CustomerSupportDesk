package com.dev21.customersupportdesksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev21.customersupportdesk.activities.AdminDashboard;
import com.dev21.customersupportdesk.activities.CustomerDashboard;
import com.dev21.customersupportdesk.activities.ProfileFillUpActivity;
import com.dev21.customersupportdesk.helper.SharedPreferenceHelper;
import com.dev21.customersupportdesk.interfaces.UserRegistrationListener;
import com.dev21.customersupportdesk.helper.UserRegistration;
import com.dev21.customersupportdesk.util.Config;

public class MainActivity extends AppCompatActivity implements UserRegistrationListener {
    private final String TAG = getClass().getSimpleName();

    private Button contactSupportBtn, adminBtn;
    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        contactSupportBtn = (Button) findViewById(R.id.contactSupportBtn);
        adminBtn = (Button) findViewById(R.id.adminBtn);

        contactSupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration userRegistration = new UserRegistration(MainActivity.this);
                userRegistration.setUserRegistrationListener(MainActivity.this);
                userRegistration.isUserRegistered("12345");
            }
        });

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, true);
                startActivity(new Intent(MainActivity.this, AdminDashboard.class));
            }
        });
    }

    @Override
    public void registrationStatus(boolean isUserRegistered) {
        if (isUserRegistered) {
            if (sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false)) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
            }
            startActivity(new Intent(this, CustomerDashboard.class));
        } else {
            startActivity(new Intent(this, ProfileFillUpActivity.class));
        }
    }
}
