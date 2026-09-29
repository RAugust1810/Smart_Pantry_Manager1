package com.smartpantry.manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        databaseHelper.getWritableDatabase();

        findViewById(R.id.btnMyPantry)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            MainActivity.this,
                            PantryActivity.class
                    );

                    startActivity(intent);
                });

        findViewById(R.id.btnSettings)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

                    startActivity(intent);
                });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }
}