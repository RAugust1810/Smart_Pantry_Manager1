package com.smartpantry.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView ingredientsRecyclerView;

    private final List<Ingredient> ingredientList =
            new ArrayList<>();

    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        databaseHelper = new DatabaseHelper(this);

        ingredientsRecyclerView =
                findViewById(R.id.ingredientsRecyclerView);

        ingredientsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new IngredientAdapter(
                ingredientList,
                new IngredientAdapter.OnIngredientActionListener() {

                    @Override
                    public void onEdit(Ingredient ingredient) {
                        editIngredient(ingredient);
                    }

                    @Override
                    public void onDelete(Ingredient ingredient) {
                        deleteIngredient(ingredient);
                    }
                }
        );

        ingredientsRecyclerView.setAdapter(adapter);

        findViewById(R.id.btnAddIngredient)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            PantryActivity.this,
                            AddIngredientActivity.class
                    );

                    startActivity(intent);
                });

        findViewById(R.id.btnViewRecipes)
                .setOnClickListener(v -> {

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

        ingredientList.clear();

        Cursor cursor =
                databaseHelper.getAllIngredients();

        while (cursor.moveToNext()) {

            int ingredientId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("name")
                    );

            double quantity =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow("quantity")
                    );

            String unit =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("unit")
                    );

            String category =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("category")
                    );

            ingredientList.add(
                    new Ingredient(
                            ingredientId,
                            name,
                            quantity,
                            unit,
                            category
                    )
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }

    private void editIngredient(Ingredient ingredient) {

        Intent intent = new Intent(
                PantryActivity.this,
                EditIngredientActivity.class
        );

        intent.putExtra(
                "ingredient_id",
                ingredient.getId()
        );

        intent.putExtra(
                "ingredient_name",
                ingredient.getName()
        );

        intent.putExtra(
                "ingredient_quantity",
                ingredient.getQuantity()
        );

        intent.putExtra(
                "ingredient_unit",
                ingredient.getUnit()
        );

        intent.putExtra(
                "ingredient_category",
                ingredient.getCategory()
        );

        startActivity(intent);
    }

    private void deleteIngredient(Ingredient ingredient) {

        boolean deleted =
                databaseHelper.deleteIngredient(
                        ingredient.getId()
                );

        if (deleted) {

            Toast.makeText(
                    PantryActivity.this,
                    "Ingredient deleted.",
                    Toast.LENGTH_SHORT
            ).show();

            loadIngredients();

        } else {

            Toast.makeText(
                    PantryActivity.this,
                    "Unable to delete ingredient.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}