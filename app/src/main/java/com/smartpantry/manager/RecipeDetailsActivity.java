package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private TextView tvRecipeName;
    private TextView tvRecipeDescription;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        databaseHelper = new DatabaseHelper(this);

        tvRecipeName = findViewById(
                R.id.tvRecipeName
        );

        tvRecipeDescription = findViewById(
                R.id.tvRecipeDescription
        );

        tvRecipeIngredients = findViewById(
                R.id.tvRecipeIngredients
        );

        tvRecipeInstructions = findViewById(
                R.id.tvRecipeInstructions
        );

        loadRecipeDetails();
    }

    private void loadRecipeDetails() {

        String recipeName = getIntent().getStringExtra(
                "recipe_name"
        );

        String description = getIntent().getStringExtra(
                "recipe_description"
        );

        String ingredients = getIntent().getStringExtra(
                "recipe_ingredients"
        );

        tvRecipeName.setText(recipeName);

        tvRecipeDescription.setText(
                description
        );

        tvRecipeIngredients.setText(
                ingredients
        );

        // Get recipe instructions from the database
        String instructions = getRecipeInstructions(
                recipeName
        );

        tvRecipeInstructions.setText(
                instructions
        );
    }

    private String getRecipeInstructions(
            String recipeName) {

        String instructions =
                "Instructions are not available.";

        android.database.Cursor cursor =
                databaseHelper.getAllRecipes();

        while (cursor.moveToNext()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            if (name.equals(recipeName)) {

                String databaseInstructions =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "instructions"
                                )
                        );

                if (databaseInstructions != null &&
                        !databaseInstructions.isEmpty()) {

                    instructions =
                            databaseInstructions;
                }

                break;
            }
        }

        cursor.close();

        return instructions;
    }
}