package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView titleText, ingredientsText, instructionsText;
    private Recipe selectedRecipe;
    private SQLiteManager dbManager;
    private RecipeListHandler recipeLibrary;
    private ArrayList<Recipe> recipeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initWidgets();
        showRecipe();
    }

    private void initWidgets() {
        titleText = findViewById(R.id.titleTextView);
        ingredientsText = findViewById(R.id.ingredientsListTextView);
        instructionsText = findViewById(R.id.instructionsTextView);

        dbManager = SQLiteManager.instanceOfDatabase(this);
        recipeArrayList = dbManager.populateRecipeListArray();
        recipeLibrary = new RecipeListHandler(recipeArrayList);
    }

    private void showRecipe() {
        Intent previousIntent = getIntent();

        int passedRecipeId = previousIntent.getIntExtra(Recipe.RECIPE_EDIT_ID, -1);
        selectedRecipe = recipeLibrary.getRecipeForID(passedRecipeId);

        if (selectedRecipe != null) {
            titleText.setText(selectedRecipe.getTitle());
            ingredientsText.setText(selectedRecipe.getIngredients());
            instructionsText.setText(selectedRecipe.getInstructions());
        }
    }

    public void goBackToLibrary(View view) {
        Intent intent = new Intent(this, RecipeLibraryActivity.class);
        startActivity(intent);
    }

    public void editRecipe(View view) {
        Intent editRecipeIntent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        editRecipeIntent.putExtra(Recipe.RECIPE_EDIT_ID, selectedRecipe.getId());
        startActivity(editRecipeIntent);
    }

    public void makeRecipe(View view) {
        Intent makeRecipeIntent = new Intent(getApplicationContext(), GroceryActivity.class);
        makeRecipeIntent.putExtra("INGREDIENTS", selectedRecipe.getIngredients());
        startActivity(makeRecipeIntent);
    }
}