package com.smartpantry.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout recipesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        databaseHelper = new DatabaseHelper(this);

        recipesContainer = findViewById(R.id.recipesContainer);

        addRecipes();

        loadRecommendedRecipes();
    }

    private void addRecipes() {

        Cursor cursor = databaseHelper.getAllRecipes();

        if (cursor.getCount() > 0) {
            cursor.close();
            return;
        }

        databaseHelper.addRecipe(
                "Chicken & Potato Bake",
                "A simple meal made with chicken, potatoes and vegetables.",
                "Chicken, Potatoes",
                "1. Cut the chicken into smaller pieces.\n" +
                        "2. Peel and cut the potatoes.\n" +
                        "3. Place the chicken and potatoes in a baking dish.\n" +
                        "4. Add vegetables and season to taste.\n" +
                        "5. Bake until the chicken and potatoes are fully cooked."
        );

        databaseHelper.addRecipe(
                "Vegetable Stir Fry",
                "A quick stir fry made with mixed vegetables.",
                "Vegetables",
                "1. Wash and cut the vegetables.\n" +
                        "2. Heat a small amount of oil in a pan.\n" +
                        "3. Add the vegetables and stir-fry.\n" +
                        "4. Cook until the vegetables are tender.\n" +
                        "5. Serve while warm."
        );

        databaseHelper.addRecipe(
                "Chicken & Vegetable Stir Fry",
                "A simple stir fry using chicken and vegetables.",
                "Chicken, Vegetables",
                "1. Cut the chicken into smaller pieces.\n" +
                        "2. Wash and cut the vegetables.\n" +
                        "3. Heat a small amount of oil in a pan.\n" +
                        "4. Cook the chicken thoroughly.\n" +
                        "5. Add the vegetables and stir-fry.\n" +
                        "6. Serve while warm."
        );

        cursor.close();
    }

    private void loadRecommendedRecipes() {

        recipesContainer.removeAllViews();

        List<String> pantryIngredients = new ArrayList<>();

        Cursor pantryCursor =
                databaseHelper.getAllIngredients();

        while (pantryCursor.moveToNext()) {

            String ingredientName =
                    pantryCursor.getString(
                            pantryCursor.getColumnIndexOrThrow("name")
                    );

            pantryIngredients.add(
                    normalizeIngredientName(ingredientName)
            );
        }

        pantryCursor.close();

        Cursor recipeCursor =
                databaseHelper.getRecommendedRecipes();

        int recipeCount = 0;

        while (recipeCursor.moveToNext()) {

            int recipeId =
                    recipeCursor.getInt(
                            recipeCursor.getColumnIndexOrThrow("id")
                    );

            String recipeName =
                    recipeCursor.getString(
                            recipeCursor.getColumnIndexOrThrow("name")
                    );

            String description =
                    recipeCursor.getString(
                            recipeCursor.getColumnIndexOrThrow("description")
                    );

            String ingredientsText =
                    recipeCursor.getString(
                            recipeCursor.getColumnIndexOrThrow("ingredients")
                    );

            String[] recipeIngredients =
                    ingredientsText.split(",");

            int matchedIngredients = 0;

            for (String ingredient : recipeIngredients) {

                String requiredIngredient =
                        normalizeIngredientName(ingredient);

                if (pantryIngredients.contains(
                        requiredIngredient)) {

                    matchedIngredients++;
                }
            }

            int totalIngredients =
                    recipeIngredients.length;

            /*
             * Strict matching rule:
             * EVERY ingredient must be available.
             *
             * Partial matches are not displayed.
             */
            boolean recipeCanBeMade =
                    matchedIngredients == totalIngredients;

            if (recipeCanBeMade) {

                LinearLayout recipeLayout =
                        new LinearLayout(this);

                recipeLayout.setOrientation(
                        LinearLayout.VERTICAL
                );

                recipeLayout.setPadding(
                        16,
                        16,
                        16,
                        16
                );

                TextView recipeView =
                        new TextView(this);

                recipeView.setText(
                        recipeName + "\n\n" +
                                description + "\n\n" +
                                "Ingredients: " +
                                ingredientsText + "\n\n" +
                                "Ingredients available: " +
                                matchedIngredients +
                                " of " +
                                totalIngredients + "\n" +
                                "Match: 100%"
                );

                recipeView.setTextSize(16);

                Button btnViewRecipe =
                        new Button(this);

                btnViewRecipe.setText(
                        "View Recipe"
                );

                btnViewRecipe.setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    RecipeActivity.this,
                                    RecipeDetailsActivity.class
                            );

                    intent.putExtra(
                            "recipe_id",
                            recipeId
                    );

                    intent.putExtra(
                            "recipe_name",
                            recipeName
                    );

                    intent.putExtra(
                            "recipe_description",
                            description
                    );

                    intent.putExtra(
                            "recipe_ingredients",
                            ingredientsText
                    );

                    startActivity(intent);
                });

                recipeLayout.addView(recipeView);
                recipeLayout.addView(btnViewRecipe);

                recipesContainer.addView(
                        recipeLayout
                );

                recipeCount++;
            }
        }

        recipeCursor.close();

        if (recipeCount == 0) {

            TextView noRecipesMessage =
                    new TextView(this);

            noRecipesMessage.setText(
                    "No recipes match your current pantry ingredients."
            );

            noRecipesMessage.setTextSize(16);

            noRecipesMessage.setPadding(
                    0,
                    16,
                    0,
                    16
            );

            recipesContainer.addView(
                    noRecipesMessage
            );
        }
    }

    /*
     * Makes simple singular/plural differences easier to match.
     * Examples:
     * tomatoes -> tomato
     * potatoes -> potato
     * apples -> apple
     */
    private String normalizeIngredientName(String name) {

        String normalized =
                name.trim().toLowerCase();

        if (normalized.endsWith("ies")
                && normalized.length() > 3) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 3
                    ) + "y";

        } else if (normalized.endsWith("oes")
                && normalized.length() > 3) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 2
                    );

        } else if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 2) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 1
                    );
        }

        return normalized;
    }
}
