package com.smartpantry.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout ingredientsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        databaseHelper = new DatabaseHelper(this);

        ingredientsContainer = findViewById(R.id.ingredientsContainer);

        findViewById(R.id.btnAddIngredient).setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        findViewById(R.id.btnViewRecipes).setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    RecipeActivity.class
            );
            startActivity(intent);
        });

        loadIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null) {
            loadIngredients();
        }
    }

    private void loadIngredients() {

        ingredientsContainer.removeAllViews();

        Cursor cursor = databaseHelper.getAllIngredients();

        if (cursor.getCount() == 0) {

            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("No ingredients added yet.");
            emptyMessage.setTextSize(16);
            emptyMessage.setPadding(0, 16, 0, 16);

            ingredientsContainer.addView(emptyMessage);

        } else {

            while (cursor.moveToNext()) {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                String category = cursor.getString(
                        cursor.getColumnIndexOrThrow("category")
                );

                View ingredientView = getLayoutInflater()
                        .inflate(
                                R.layout.item_ingredient,
                                ingredientsContainer,
                                false
                        );

                TextView tvName = ingredientView.findViewById(
                        R.id.tvIngredientName
                );

                TextView tvQuantity = ingredientView.findViewById(
                        R.id.tvIngredientQuantity
                );

                TextView tvCategory = ingredientView.findViewById(
                        R.id.tvIngredientCategory
                );

                tvName.setText(name);
                tvQuantity.setText(quantity + " " + unit);
                tvCategory.setText(
                        category.isEmpty()
                                ? "No category"
                                : category
                );

                ingredientsContainer.addView(ingredientView);
            }
        }

        cursor.close();
    }
}