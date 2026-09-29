package com.smartpantry.manager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME =
            "SmartPantrySettings";

    private static final String EXPIRY_ALERTS_KEY =
            "expiry_alerts_enabled";

    private Switch switchExpiryAlerts;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        preferences =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );

        boolean expiryAlertsEnabled =
                preferences.getBoolean(
                        EXPIRY_ALERTS_KEY,
                        false
                );

        switchExpiryAlerts.setChecked(
                expiryAlertsEnabled
        );

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean(
                                    EXPIRY_ALERTS_KEY,
                                    isChecked
                            )
                            .apply();

                    String message;

                    if (isChecked) {
                        message =
                                "Expiring soon alerts enabled.";
                    } else {
                        message =
                                "Expiring soon alerts disabled.";
                    }

                    Toast.makeText(
                            SettingsActivity.this,
                            message,
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }
}