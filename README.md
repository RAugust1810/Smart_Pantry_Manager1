# Smart Pantry Manager

## Project Description

Smart Pantry Manager is a Java-based Android application designed to help users manage ingredients stored in their pantry and reduce food waste.

The application allows users to add, view, edit and delete pantry ingredients. It also contains a collection of pre-loaded recipes and recommends recipes based strictly on the ingredients and quantities currently available in the user's pantry.

The main purpose of the application is to help users make use of ingredients they already have instead of allowing them to go unused.

## Main Features

* Add pantry ingredients
* View current pantry ingredients
* Edit existing ingredients
* Delete pantry ingredients
* Store pantry data locally
* SQLite database persistence
* 15 pre-loaded recipes
* Strict recipe matching
* Quantity-aware recipe matching
* Unit-aware recipe matching
* Recipe details and preparation instructions
* Settings screen
* Persistent application settings
* RecyclerView with a custom Adapter
* Input validation
* Navigation between application screens

## Strict Recipe Matching

The application uses a strict recipe matching rule.

A recipe is displayed as a recommended recipe only when **every required ingredient is available in the pantry in a sufficient quantity**.

For example, if a recipe requires:

* Chicken — 1 kg
* Potatoes — 2 kg

the recipe will only be recommended when both ingredients are available and the required quantities are met.

If the user has the required Chicken but does not have the Potatoes, the recipe is not displayed.

This prevents the application from recommending recipes that the user cannot currently prepare.

## Database

The application uses **SQLite** for local data storage.

SQLite was selected because it is built into the Android platform, does not require an external database server, and is suitable for storing the application's pantry and recipe data locally.

The database is managed using Android's `SQLiteOpenHelper`.

The main database tables are:

* `ingredients` — stores the user's pantry ingredients
* `recipes` — stores recipe information
* `recipe_ingredients` — stores the individual ingredient requirements for each recipe

The database allows pantry information to remain available when the application is closed and reopened.

## Technologies Used

* Java
* Android Studio
* Android SDK
* SQLite
* SQLiteOpenHelper
* RecyclerView
* Custom RecyclerView Adapter
* Android Intents
* SharedPreferences
* Git
* GitHub

## Application Screens

The application contains the following main screens:

1. **Home Screen** — provides access to the main application functions.
2. **My Pantry** — displays ingredients currently stored in the pantry.
3. **Add Ingredient** — allows new pantry ingredients to be added.
4. **Edit Ingredient** — allows existing ingredients to be updated.
5. **Recommended Recipes** — displays recipes that can currently be prepared.
6. **Recipe Details** — displays recipe ingredients and preparation instructions.
7. **Settings** — allows the user to manage application preferences.

## Data Persistence

Pantry information is stored in the SQLite database rather than only being held in memory.

This means that information remains available after the application is closed and reopened.

Application settings are stored using Android SharedPreferences.

## Project Structure

The main application code is located under:

`app/src/main/java/com/smartpantry/manager/`

The application's layouts and other resources are located under:

`app/src/main/res/`

The SQLite database logic is contained in:

`DatabaseHelper.java`

## How to Run the Project

### Requirements

* Android Studio
* Android SDK
* Java Development Kit
* Android emulator or Android device
* Git

### Steps

1. Clone or download the project from the GitHub repository.
2. Open the project in Android Studio.
3. Allow Android Studio to synchronise the Gradle project.
4. Connect an Android device or start an Android emulator.
5. Build the project using Android Studio.
6. Run the application.

The application will create and initialise its SQLite database when it is first started.

## Version Control

The project was developed using Git throughout the development process.

Git was used to track development changes and maintain a history of the application's implementation.

The GitHub repository contains the project source code and development history.

## Project Purpose

Smart Pantry Manager was developed as a Mobile App Development 700 practical assignment.

The project demonstrates Android application development using Java, database management with SQLite, CRUD functionality, RecyclerView and custom adapters, Intents, persistent settings, input validation and rule-based recipe matching.
