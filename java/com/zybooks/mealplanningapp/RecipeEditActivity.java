package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecipeEditActivity extends AppCompatActivity {

    private EditText titleEditText, ingredientsEditText, instructionsEditText;
    private Button deleteButton;
    private Recipe selectedRecipe;

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
    }

    private void checkForEditRecipe() {
        Intent previousIntent = getIntent();

        int passedRecipeId = previousIntent.getIntExtra(Recipe.RECIPE_EDIT_EXTRA, -1);
        selectedRecipe = Recipe.getRecipeForID(passedRecipeId);

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

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String ingredients = String.valueOf(ingredientsEditText.getText());
        String instructions = String.valueOf(instructionsEditText.getText());

        if (selectedRecipe == null) {
            int id = Recipe.recipeArrayList.size();
            Recipe newRecipe = new Recipe(id, title, ingredients, instructions);
            Recipe.recipeArrayList.add(newRecipe);
            sqLiteManager.addRecipeToDatabase(newRecipe);
        }
        else {
            selectedRecipe.setTitle(title);
            selectedRecipe.setIngredients(ingredients);
            selectedRecipe.setInstructions(instructions);
            sqLiteManager.updateRecipeInDB(selectedRecipe);
        }

        finish();
    }

    public void deleteRecipe(View view) {

        selectedRecipe.setDeleted(true);
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateRecipeInDB(selectedRecipe);

        finish();
    }
}