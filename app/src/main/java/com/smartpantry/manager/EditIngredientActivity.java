package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditIngredientActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText etName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etCategory;

    private int ingredientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);

        databaseHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etEditIngredientName);
        etQuantity = findViewById(R.id.etEditQuantity);
        etUnit = findViewById(R.id.etEditUnit);
        etCategory = findViewById(R.id.etEditCategory);

        Button btnUpdate = findViewById(
                R.id.btnUpdateIngredient
        );

        ingredientId = getIntent().getIntExtra(
                "ingredient_id",
                -1
        );

        String name = getIntent().getStringExtra(
                "ingredient_name"
        );

        double quantity = getIntent().getDoubleExtra(
                "ingredient_quantity",
                0
        );

        String unit = getIntent().getStringExtra(
                "ingredient_unit"
        );

        String category = getIntent().getStringExtra(
                "ingredient_category"
        );

        etName.setText(name);
        etQuantity.setText(String.valueOf(quantity));
        etUnit.setText(unit);
        etCategory.setText(category);

        btnUpdate.setOnClickListener(v -> updateIngredient());
    }

    private void updateIngredient() {

        String name = etName.getText()
                .toString()
                .trim();

        String quantityText = etQuantity.getText()
                .toString()
                .trim();

        String unit = etUnit.getText()
                .toString()
                .trim();

        String category = etCategory.getText()
                .toString()
                .trim();

        if (name.isEmpty()) {

            etName.setError(
                    "Please enter an ingredient name."
            );

            return;
        }

        if (quantityText.isEmpty()) {

            etQuantity.setError(
                    "Please enter a quantity."
            );

            return;
        }

        if (unit.isEmpty()) {

            etUnit.setError(
                    "Please enter a unit."
            );

            return;
        }

        if (category.isEmpty()) {

            etCategory.setError(
                    "Please enter a category."
            );

            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Please enter a valid number."
            );

            return;
        }

        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than zero."
            );

            return;
        }

        boolean updated =
                databaseHelper.updateIngredient(
                        ingredientId,
                        name,
                        quantity,
                        unit,
                        category
                );

        if (updated) {

            Toast.makeText(
                    this,
                    "Ingredient updated successfully!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Unable to update ingredient.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}