package com.smartpantry.manager;

import android.database.Cursor;
import android.os.Bundle;
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
                "Chicken, Potatoes"
        );

        databaseHelper.addRecipe(
                "Vegetable Stir Fry",
                "A quick stir fry made with mixed vegetables.",
                "Vegetables"
        );

        databaseHelper.addRecipe(
                "Chicken & Vegetable Stir Fry",
                "A simple stir fry using chicken and vegetables.",
                "Chicken, Vegetables"
        );

        cursor.close();
    }

    private void loadRecommendedRecipes() {

        recipesContainer.removeAllViews();

        // Get ingredients currently in the pantry
        List<String> pantryIngredients = new ArrayList<>();

        Cursor pantryCursor = databaseHelper.getAllIngredients();

        while (pantryCursor.moveToNext()) {

            String ingredientName = pantryCursor.getString(
                    pantryCursor.getColumnIndexOrThrow("name")
            );

            pantryIngredients.add(
                    ingredientName.trim().toLowerCase()
            );
        }

        pantryCursor.close();

        // Get all recipes
        Cursor recipeCursor = databaseHelper.getRecommendedRecipes();

        int recipeCount = 0;

        while (recipeCursor.moveToNext()) {

            String recipeName = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("name")
            );

            String description = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("description")
            );

            String ingredientsText = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("ingredients")
            );

            String[] recipeIngredients =
                    ingredientsText.split(",");

            int matchedIngredients = 0;

            for (String ingredient : recipeIngredients) {

                String requiredIngredient =
                        ingredient.trim().toLowerCase();

                if (pantryIngredients.contains(requiredIngredient)) {
                    matchedIngredients++;
                }
            }

            int totalIngredients = recipeIngredients.length;

            int matchPercentage =
                    (matchedIngredients * 100) / totalIngredients;

            // Only display recipes with at least one matching ingredient
            if (matchedIngredients > 0) {

                TextView recipeView = new TextView(this);

                recipeView.setText(
                        recipeName + "\n\n" +
                                description + "\n\n" +
                                "Ingredients: " + ingredientsText + "\n\n" +
                                "Ingredients available: " +
                                matchedIngredients + " of " +
                                totalIngredients + "\n" +
                                "Match: " +
                                matchPercentage + "%"
                );

                recipeView.setTextSize(16);
                recipeView.setPadding(16, 16, 16, 16);

                recipesContainer.addView(recipeView);

                recipeCount++;
            }
        }

        recipeCursor.close();

        // Show a message if there are no matching recipes
        if (recipeCount == 0) {

            TextView noRecipesMessage = new TextView(this);

            noRecipesMessage.setText(
                    "No recipes match your current pantry ingredients."
            );

            noRecipesMessage.setTextSize(16);
            noRecipesMessage.setPadding(0, 16, 0, 16);

            recipesContainer.addView(noRecipesMessage);
        }
    }
}