package com.smartpantry.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        // Recipe 1
        addRecipeIfMissing(
                "Chicken & Potato Bake",
                "A simple meal made with chicken, potatoes and vegetables.",
                "Chicken, Potatoes",
                "1. Cut the chicken into smaller pieces.\n" +
                        "2. Peel and cut the potatoes.\n" +
                        "3. Place the chicken and potatoes in a baking dish.\n" +
                        "4. Add vegetables and season to taste.\n" +
                        "5. Bake until the chicken and potatoes are fully cooked.",
                new String[]{"Chicken", "Potatoes"},
                new double[]{1, 2},
                new String[]{"kg", "kg"}
        );

        // Recipe 2
        addRecipeIfMissing(
                "Vegetable Stir Fry",
                "A quick stir fry made with mixed vegetables.",
                "Vegetables",
                "1. Wash and cut the vegetables.\n" +
                        "2. Heat a small amount of oil in a pan.\n" +
                        "3. Add the vegetables and stir-fry.\n" +
                        "4. Cook until the vegetables are tender.\n" +
                        "5. Serve while warm.",
                new String[]{"Vegetables"},
                new double[]{2},
                new String[]{"kg"}
        );

        // Recipe 3
        addRecipeIfMissing(
                "Chicken & Vegetable Stir Fry",
                "A simple stir fry using chicken and vegetables.",
                "Chicken, Vegetables",
                "1. Cut the chicken into smaller pieces.\n" +
                        "2. Wash and cut the vegetables.\n" +
                        "3. Heat a small amount of oil in a pan.\n" +
                        "4. Cook the chicken thoroughly.\n" +
                        "5. Add the vegetables and stir-fry.\n" +
                        "6. Serve while warm.",
                new String[]{"Chicken", "Vegetables"},
                new double[]{1, 2},
                new String[]{"kg", "kg"}
        );

        // Recipe 4
        addRecipeIfMissing(
                "Beef & Rice Bowl",
                "A simple rice bowl made with beef and vegetables.",
                "Beef, Rice",
                "1. Cook the rice according to the package instructions.\n" +
                        "2. Cut the beef into small pieces.\n" +
                        "3. Cook the beef in a pan until fully cooked.\n" +
                        "4. Add the cooked rice and stir together.\n" +
                        "5. Serve while warm.",
                new String[]{"Beef", "Rice"},
                new double[]{1, 2},
                new String[]{"kg", "kg"}
        );

        // Recipe 5
        addRecipeIfMissing(
                "Tomato Pasta",
                "A simple pasta meal prepared with tomatoes.",
                "Pasta, Tomatoes",
                "1. Cook the pasta until tender.\n" +
                        "2. Wash and chop the tomatoes.\n" +
                        "3. Cook the tomatoes in a pan.\n" +
                        "4. Add the cooked pasta and mix well.\n" +
                        "5. Serve while warm.",
                new String[]{"Pasta", "Tomatoes"},
                new double[]{1, 1},
                new String[]{"kg", "kg"}
        );

        // Recipe 6
        addRecipeIfMissing(
                "Cheese Omelette",
                "A quick omelette made with eggs and cheese.",
                "Eggs, Cheese",
                "1. Crack the eggs into a bowl and whisk them.\n" +
                        "2. Heat a small amount of oil in a pan.\n" +
                        "3. Pour the eggs into the pan.\n" +
                        "4. Add the cheese and fold the omelette.\n" +
                        "5. Cook until the eggs are fully cooked.",
                new String[]{"Eggs", "Cheese"},
                new double[]{4, 0.2},
                new String[]{"items", "kg"}
        );

        // Recipe 7
        addRecipeIfMissing(
                "Tuna Pasta",
                "A quick pasta meal made with tuna.",
                "Pasta, Tuna",
                "1. Cook the pasta until tender.\n" +
                        "2. Drain the tuna.\n" +
                        "3. Mix the tuna with the cooked pasta.\n" +
                        "4. Add seasoning if required.\n" +
                        "5. Serve while warm.",
                new String[]{"Pasta", "Tuna"},
                new double[]{1, 2},
                new String[]{"kg", "items"}
        );

        // Recipe 8
        addRecipeIfMissing(
                "Beef & Potato Stew",
                "A warm stew made with beef and potatoes.",
                "Beef, Potatoes, Carrots",
                "1. Cut the beef and vegetables into smaller pieces.\n" +
                        "2. Brown the beef in a pot.\n" +
                        "3. Add the potatoes and carrots.\n" +
                        "4. Add water and allow the stew to simmer.\n" +
                        "5. Cook until the beef and vegetables are tender.",
                new String[]{"Beef", "Potatoes", "Carrots"},
                new double[]{1, 2, 1},
                new String[]{"kg", "kg", "kg"}
        );

        // Recipe 9
        addRecipeIfMissing(
                "Chicken & Rice",
                "A simple chicken and rice meal.",
                "Chicken, Rice",
                "1. Cook the rice until tender.\n" +
                        "2. Cut the chicken into smaller pieces.\n" +
                        "3. Cook the chicken thoroughly in a pan.\n" +
                        "4. Add the cooked rice.\n" +
                        "5. Mix together and serve.",
                new String[]{"Chicken", "Rice"},
                new double[]{1, 2},
                new String[]{"kg", "kg"}
        );

        // Recipe 10
        addRecipeIfMissing(
                "Tomato & Cheese Pasta",
                "Pasta combined with tomatoes and cheese.",
                "Pasta, Tomatoes, Cheese",
                "1. Cook the pasta until tender.\n" +
                        "2. Chop and cook the tomatoes.\n" +
                        "3. Add the cooked pasta.\n" +
                        "4. Add the cheese and stir together.\n" +
                        "5. Serve while warm.",
                new String[]{"Pasta", "Tomatoes", "Cheese"},
                new double[]{1, 1, 0.2},
                new String[]{"kg", "kg", "kg"}
        );

        // Recipe 11
        addRecipeIfMissing(
                "Vegetable Soup",
                "A simple soup made with potatoes, carrots and vegetables.",
                "Potatoes, Carrots, Vegetables",
                "1. Wash and chop all the vegetables.\n" +
                        "2. Add the vegetables to a pot of water.\n" +
                        "3. Bring the mixture to a boil.\n" +
                        "4. Simmer until all vegetables are tender.\n" +
                        "5. Season and serve warm.",
                new String[]{"Potatoes", "Carrots", "Vegetables"},
                new double[]{1, 1, 2},
                new String[]{"kg", "kg", "kg"}
        );

        // Recipe 12
        addRecipeIfMissing(
                "Beef Stir Fry",
                "A quick stir fry made with beef and vegetables.",
                "Beef, Vegetables",
                "1. Cut the beef into thin pieces.\n" +
                        "2. Wash and cut the vegetables.\n" +
                        "3. Heat oil in a pan.\n" +
                        "4. Cook the beef thoroughly.\n" +
                        "5. Add the vegetables and stir-fry until tender.",
                new String[]{"Beef", "Vegetables"},
                new double[]{1, 2},
                new String[]{"kg", "kg"}
        );

        // Recipe 13
        addRecipeIfMissing(
                "Chicken Sandwich",
                "A simple sandwich made with chicken and tomatoes.",
                "Chicken, Bread, Tomatoes",
                "1. Cook the chicken thoroughly.\n" +
                        "2. Slice the tomatoes.\n" +
                        "3. Place the chicken and tomatoes between slices of bread.\n" +
                        "4. Add seasoning if required.\n" +
                        "5. Serve immediately.",
                new String[]{"Chicken", "Bread", "Tomatoes"},
                new double[]{1, 4, 0.5},
                new String[]{"kg", "items", "kg"}
        );

        // Recipe 14
        addRecipeIfMissing(
                "Egg Fried Rice",
                "A simple fried rice meal made with eggs and rice.",
                "Eggs, Rice, Vegetables",
                "1. Cook the rice and allow it to cool slightly.\n" +
                        "2. Beat the eggs in a bowl.\n" +
                        "3. Cook the eggs in a pan.\n" +
                        "4. Add the rice and vegetables.\n" +
                        "5. Stir-fry everything together and serve.",
                new String[]{"Eggs", "Rice", "Vegetables"},
                new double[]{4, 2, 1},
                new String[]{"items", "kg", "kg"}
        );

        // Recipe 15
        addRecipeIfMissing(
                "Potato Omelette",
                "An omelette made with potatoes and eggs.",
                "Potatoes, Eggs",
                "1. Peel and slice the potatoes.\n" +
                        "2. Cook the potatoes until tender.\n" +
                        "3. Beat the eggs in a bowl.\n" +
                        "4. Add the eggs to the potatoes.\n" +
                        "5. Cook until the eggs are fully set.",
                new String[]{"Potatoes", "Eggs"},
                new double[]{1, 4},
                new String[]{"kg", "items"}
        );
    }

    private void addRecipeIfMissing(
            String name,
            String description,
            String ingredients,
            String instructions,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        int existingRecipeId =
                getRecipeIdByName(name);

        if (existingRecipeId != -1) {
            return;
        }

        boolean recipeAdded =
                databaseHelper.addRecipe(
                        name,
                        description,
                        ingredients,
                        instructions
                );

        if (!recipeAdded) {
            return;
        }

        int recipeId =
                getRecipeIdByName(name);

        if (recipeId == -1) {
            return;
        }

        for (int i = 0;
             i < ingredientNames.length;
             i++) {

            databaseHelper.addRecipeIngredient(
                    recipeId,
                    ingredientNames[i],
                    quantities[i],
                    units[i]
            );
        }
    }

    private int getRecipeIdByName(String recipeName) {

        Cursor cursor =
                databaseHelper.getAllRecipes();

        int recipeId = -1;

        while (cursor.moveToNext()) {

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("name")
                    );

            if (name.equalsIgnoreCase(recipeName)) {

                recipeId =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow("id")
                        );

                break;
            }
        }

        cursor.close();

        return recipeId;
    }

    private void loadRecommendedRecipes() {

        recipesContainer.removeAllViews();

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

            Cursor recipeIngredientsCursor =
                    databaseHelper.getRecipeIngredients(recipeId);

            int requiredIngredients = 0;
            int availableIngredients = 0;

            while (recipeIngredientsCursor.moveToNext()) {

                requiredIngredients++;

                String requiredName =
                        recipeIngredientsCursor.getString(
                                recipeIngredientsCursor
                                        .getColumnIndexOrThrow(
                                                "ingredient_name"
                                        )
                        );

                double requiredQuantity =
                        recipeIngredientsCursor.getDouble(
                                recipeIngredientsCursor
                                        .getColumnIndexOrThrow(
                                                "required_quantity"
                                        )
                        );

                String requiredUnit =
                        recipeIngredientsCursor.getString(
                                recipeIngredientsCursor
                                        .getColumnIndexOrThrow(
                                                "unit"
                                        )
                        );

                Cursor pantryCursor =
                        databaseHelper.getAllIngredients();

                boolean ingredientAvailable = false;

                while (pantryCursor.moveToNext()) {

                    String pantryName =
                            pantryCursor.getString(
                                    pantryCursor
                                            .getColumnIndexOrThrow(
                                                    "name"
                                            )
                            );

                    double pantryQuantity =
                            pantryCursor.getDouble(
                                    pantryCursor
                                            .getColumnIndexOrThrow(
                                                    "quantity"
                                            )
                            );

                    String pantryUnit =
                            pantryCursor.getString(
                                    pantryCursor
                                            .getColumnIndexOrThrow(
                                                    "unit"
                                            )
                            );

                    String normalizedPantryName =
                            normalizeIngredientName(
                                    pantryName
                            );

                    String normalizedRequiredName =
                            normalizeIngredientName(
                                    requiredName
                            );

                    boolean nameMatches =
                            normalizedPantryName.equals(
                                    normalizedRequiredName
                            );

                    boolean unitMatches =
                            pantryUnit.trim()
                                    .equalsIgnoreCase(
                                            requiredUnit.trim()
                                    );

                    boolean quantityMatches =
                            pantryQuantity >= requiredQuantity;

                    if (nameMatches &&
                            unitMatches &&
                            quantityMatches) {

                        ingredientAvailable = true;
                        break;
                    }
                }

                pantryCursor.close();

                if (ingredientAvailable) {
                    availableIngredients++;
                }
            }

            recipeIngredientsCursor.close();

            /*
             * Strict recipe matching:
             *
             * A recipe is displayed only when ALL
             * required ingredients are available
             * in the required quantities and units.
             */
            boolean recipeCanBeMade =
                    requiredIngredients > 0 &&
                            availableIngredients == requiredIngredients;

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
                                "All required ingredients " +
                                "and quantities are available.\n" +
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
                    "No recipes match your current pantry " +
                            "ingredients and quantities."
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
     *
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
