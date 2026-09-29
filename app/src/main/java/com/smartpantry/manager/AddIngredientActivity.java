package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etCategory;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        etIngredientName = findViewById(
                R.id.etIngredientName
        );

        etQuantity = findViewById(
                R.id.etQuantity
        );

        etUnit = findViewById(
                R.id.etUnit
        );

        etCategory = findViewById(
                R.id.etCategory
        );

        Button btnSaveIngredient = findViewById(
                R.id.btnSaveIngredient
        );

        databaseHelper = new DatabaseHelper(this);

        btnSaveIngredient.setOnClickListener(
                v -> saveIngredient()
        );
    }

    private void saveIngredient() {

        String name =
                etIngredientName.getText()
                        .toString()
                        .trim();

        String quantityText =
                etQuantity.getText()
                        .toString()
                        .trim();

        String unit =
                etUnit.getText()
                        .toString()
                        .trim();

        String category =
                etCategory.getText()
                        .toString()
                        .trim();

        // Check ingredient name
        if (name.isEmpty()) {

            etIngredientName.setError(
                    "Please enter an ingredient name."
            );

            etIngredientName.requestFocus();

            return;
        }

        // Check quantity
        if (quantityText.isEmpty()) {

            etQuantity.setError(
                    "Please enter a quantity."
            );

            etQuantity.requestFocus();

            return;
        }

        // Check unit
        if (unit.isEmpty()) {

            etUnit.setError(
                    "Please enter a unit."
            );

            etUnit.requestFocus();

            return;
        }

        // Check category
        if (category.isEmpty()) {

            etCategory.setError(
                    "Please enter a category."
            );

            etCategory.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(
                    quantityText
            );

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Please enter a valid number."
            );

            etQuantity.requestFocus();

            return;
        }

        // Quantity must be greater than zero
        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than zero."
            );

            etQuantity.requestFocus();

            return;
        }

        boolean success =
                databaseHelper.addIngredient(
                        name,
                        quantity,
                        unit,
                        category
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Ingredient saved successfully!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Unable to save ingredient.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}