package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RecipeEditActivity extends AppCompatActivity {

    private EditText titleEditText, ingredientsEditText, instructionsEditText;
    private Button deleteButton;
    private Recipe selectedRecipe;
    private SQLiteManager dbManager;
    private RecipeListHandler recipeLibrary;
    private ArrayList<Recipe> recipeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);
        initWidgets();
        checkForEditRecipe();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        deleteButton = findViewById(R.id.deleteRecipeButton);

        dbManager = SQLiteManager.instanceOfDatabase(this);
        recipeArrayList = dbManager.populateRecipeListArray();
        recipeLibrary = new RecipeListHandler(recipeArrayList);
    }

    private void checkForEditRecipe() {
        Intent previousIntent = getIntent();

        int passedRecipeId = previousIntent.getIntExtra(Recipe.RECIPE_EDIT_ID, -1);
        selectedRecipe = recipeLibrary.getRecipeForID(passedRecipeId);

        if (selectedRecipe != null) {
            titleEditText.setText(selectedRecipe.getTitle());
            ingredientsEditText.setText(selectedRecipe.getIngredients());
            instructionsEditText.setText(selectedRecipe.getInstructions());
        }
        else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveRecipe(View view) {
        String newtitle = String.valueOf(titleEditText.getText());
        String newingredients = String.valueOf(ingredientsEditText.getText());
        String newinstructions = String.valueOf(instructionsEditText.getText());

        if (selectedRecipe == null) {
            dbManager.addRecipeToDatabase(newtitle, newingredients, newinstructions);
        }
        else {
            int recipeId = selectedRecipe.getId();
            dbManager.updateRecipeInDB(recipeId, newtitle, newingredients, newinstructions, 0);
        }

        Intent i = new Intent(RecipeEditActivity.this, RecipeLibraryActivity.class);
        startActivity(i);
    }

    public void deleteRecipe(View view) {
        int recipeId = selectedRecipe.getId();
        String title = selectedRecipe.getTitle();
        String ingredients = selectedRecipe.getIngredients();
        String instructions = selectedRecipe.getInstructions();

        selectedRecipe.setDeleted(true);
        dbManager.updateRecipeInDB(recipeId, title, ingredients, instructions, 1);


        Intent i = new Intent(RecipeEditActivity.this, RecipeLibraryActivity.class);
        startActivity(i);
    }
}