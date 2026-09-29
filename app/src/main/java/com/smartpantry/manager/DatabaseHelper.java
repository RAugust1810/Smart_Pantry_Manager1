package com.smartpantry.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable = "CREATE TABLE ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "category TEXT" +
                ")";

        db.execSQL(createIngredientsTable);

        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "ingredients TEXT NOT NULL, " +
                "instructions TEXT" +
                ")";

        db.execSQL(createRecipesTable);

        // Stores the individual ingredients and quantities required
        // for each recipe.
        String createRecipeIngredientsTable =
                "CREATE TABLE recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "required_quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Upgrade from database version 3 to version 4
        // without deleting existing pantry ingredients.
        if (oldVersion < 4) {

            db.execSQL(
                    "ALTER TABLE recipes ADD COLUMN instructions TEXT"
            );

            // Add instructions to the existing recipes
            db.execSQL(
                    "UPDATE recipes SET instructions = ? " +
                            "WHERE name = ?",
                    new String[]{
                            "1. Cut the chicken into smaller pieces.\n" +
                                    "2. Peel and cut the potatoes.\n" +
                                    "3. Place the chicken and potatoes in a baking dish.\n" +
                                    "4. Add vegetables and season to taste.\n" +
                                    "5. Bake until the chicken and potatoes are fully cooked.",
                            "Chicken & Potato Bake"
                    }
            );

            db.execSQL(
                    "UPDATE recipes SET instructions = ? " +
                            "WHERE name = ?",
                    new String[]{
                            "1. Wash and cut the vegetables.\n" +
                                    "2. Heat a small amount of oil in a pan.\n" +
                                    "3. Add the vegetables and stir-fry.\n" +
                                    "4. Cook until the vegetables are tender.\n" +
                                    "5. Serve while warm.",
                            "Vegetable Stir Fry"
                    }
            );

            db.execSQL(
                    "UPDATE recipes SET instructions = ? " +
                            "WHERE name = ?",
                    new String[]{
                            "1. Cut the chicken into smaller pieces.\n" +
                                    "2. Wash and cut the vegetables.\n" +
                                    "3. Heat a small amount of oil in a pan.\n" +
                                    "4. Cook the chicken thoroughly.\n" +
                                    "5. Add the vegetables and stir-fry.\n" +
                                    "6. Serve while warm.",
                            "Chicken & Vegetable Stir Fry"
                    }
            );
        }

        // Upgrade from database version 4 to version 5.
        // This adds recipe quantity requirements without
        // deleting existing pantry ingredients or recipes.
        if (oldVersion < 5) {

            String createRecipeIngredientsTable =
                    "CREATE TABLE recipe_ingredients (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "recipe_id INTEGER NOT NULL, " +
                            "ingredient_name TEXT NOT NULL, " +
                            "required_quantity REAL NOT NULL, " +
                            "unit TEXT NOT NULL" +
                            ")";

            db.execSQL(createRecipeIngredientsTable);

            // Chicken & Potato Bake
            db.execSQL(
                    "INSERT INTO recipe_ingredients " +
                            "(recipe_id, ingredient_name, required_quantity, unit) " +
                            "SELECT id, 'Chicken', 1, 'kg' " +
                            "FROM recipes " +
                            "WHERE name = 'Chicken & Potato Bake'"
            );

            db.execSQL(
                    "INSERT INTO recipe_ingredients " +
                            "(recipe_id, ingredient_name, required_quantity, unit) " +
                            "SELECT id, 'Potatoes', 2, 'kg' " +
                            "FROM recipes " +
                            "WHERE name = 'Chicken & Potato Bake'"
            );

            // Vegetable Stir Fry
            db.execSQL(
                    "INSERT INTO recipe_ingredients " +
                            "(recipe_id, ingredient_name, required_quantity, unit) " +
                            "SELECT id, 'Vegetables', 2, 'kg' " +
                            "FROM recipes " +
                            "WHERE name = 'Vegetable Stir Fry'"
            );

            // Chicken & Vegetable Stir Fry
            db.execSQL(
                    "INSERT INTO recipe_ingredients " +
                            "(recipe_id, ingredient_name, required_quantity, unit) " +
                            "SELECT id, 'Chicken', 1, 'kg' " +
                            "FROM recipes " +
                            "WHERE name = 'Chicken & Vegetable Stir Fry'"
            );

            db.execSQL(
                    "INSERT INTO recipe_ingredients " +
                            "(recipe_id, ingredient_name, required_quantity, unit) " +
                            "SELECT id, 'Vegetables', 2, 'kg' " +
                            "FROM recipes " +
                            "WHERE name = 'Chicken & Vegetable Stir Fry'"
            );
        }
    }

    // Add a new ingredient to the pantry
    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            String category) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("category", category);

        long result = db.insert(
                "ingredients",
                null,
                values
        );

        return result != -1;
    }

    // Get all pantry ingredients
    public Cursor getAllIngredients() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM ingredients ORDER BY id DESC",
                null
        );
    }

    // Add a new recipe
    public boolean addRecipe(
            String name,
            String description,
            String ingredients,
            String instructions) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("ingredients", ingredients);
        values.put("instructions", instructions);

        long result = db.insert(
                "recipes",
                null,
                values
        );

        return result != -1;
    }

    // Get all recipes
    public Cursor getAllRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM recipes ORDER BY id ASC",
                null
        );
    }

    // Get recipes for recommendation
    public Cursor getRecommendedRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM recipes",
                null
        );
    }

    // Add an ingredient requirement to a recipe
    public boolean addRecipeIngredient(
            int recipeId,
            String ingredientName,
            double requiredQuantity,
            String unit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("recipe_id", recipeId);
        values.put("ingredient_name", ingredientName);
        values.put("required_quantity", requiredQuantity);
        values.put("unit", unit);

        long result = db.insert(
                "recipe_ingredients",
                null,
                values
        );

        return result != -1;
    }

    // Get all ingredient requirements for a recipe
    public Cursor getRecipeIngredients(int recipeId) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM recipe_ingredients " +
                        "WHERE recipe_id = ?",
                new String[]{
                        String.valueOf(recipeId)
                }
        );
    }

    // Delete ingredient
    public boolean deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "ingredients",
                "id = ?",
                new String[]{
                        String.valueOf(id)
                }
        );

        return result > 0;
    }

    // Update ingredient
    public boolean updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String category) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("category", category);

        int result = db.update(
                "ingredients",
                values,
                "id = ?",
                new String[]{
                        String.valueOf(id)
                }
        );

        return result > 0;
    }
}
