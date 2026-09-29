package com.smartpantry.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView ingredientsRecyclerView;

    private final List<Ingredient> ingredientList = new ArrayList<>();
    private RecyclerView.Adapter<IngredientViewHolder> adapter;

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

        adapter = new RecyclerView.Adapter<IngredientViewHolder>() {

            @Override
            public IngredientViewHolder onCreateViewHolder(
                    ViewGroup parent,
                    int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_ingredient,
                                parent,
                                false
                        );

                return new IngredientViewHolder(view);
            }

            @Override
            public void onBindViewHolder(
                    IngredientViewHolder holder,
                    int position) {

                Ingredient ingredient =
                        ingredientList.get(position);

                holder.tvName.setText(
                        ingredient.name
                );

                holder.tvQuantity.setText(
                        ingredient.quantity + " " + ingredient.unit
                );

                holder.tvCategory.setText(
                        ingredient.category == null ||
                                ingredient.category.isEmpty()
                                ? "No category"
                                : ingredient.category
                );

                holder.btnEdit.setOnClickListener(v -> {

                    Intent intent = new Intent(
                            PantryActivity.this,
                            EditIngredientActivity.class
                    );

                    intent.putExtra(
                            "ingredient_id",
                            ingredient.id
                    );

                    intent.putExtra(
                            "ingredient_name",
                            ingredient.name
                    );

                    intent.putExtra(
                            "ingredient_quantity",
                            ingredient.quantity
                    );

                    intent.putExtra(
                            "ingredient_unit",
                            ingredient.unit
                    );

                    intent.putExtra(
                            "ingredient_category",
                            ingredient.category
                    );

                    startActivity(intent);
                });

                holder.btnDelete.setOnClickListener(v -> {

                    boolean deleted =
                            databaseHelper.deleteIngredient(
                                    ingredient.id
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
                });
            }

            @Override
            public int getItemCount() {
                return ingredientList.size();
            }
        };

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

    private static class Ingredient {

        int id;
        String name;
        double quantity;
        String unit;
        String category;

        Ingredient(
                int id,
                String name,
                double quantity,
                String unit,
                String category) {

            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
            this.category = category;
        }
    }

    private static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvQuantity;
        TextView tvCategory;

        Button btnEdit;
        Button btnDelete;

        IngredientViewHolder(View itemView) {
            super(itemView);

            tvName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvQuantity =
                    itemView.findViewById(
                            R.id.tvIngredientQuantity
                    );

            tvCategory =
                    itemView.findViewById(
                            R.id.tvIngredientCategory
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}