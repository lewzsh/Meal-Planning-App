package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button recipeLibraryButton;
    private Button newRecipeButton;
    private Button groceryListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
    }

    private void initWidgets() {
        recipeLibraryButton = findViewById(R.id.recipeLibraryButton);
        newRecipeButton = findViewById(R.id.newRecipeButton);
        groceryListButton = findViewById(R.id.groceryListButton);
    }

    public void openRecipeLibrary(View view) {
        Intent openLibraryIntent = new Intent(this, RecipeLibraryActivity.class);
        startActivity(openLibraryIntent);
    }

    public void addNewRecipe(View view) {
        Intent addRecipeIntent = new Intent(this, RecipeEditActivity.class);
        startActivity(addRecipeIntent);
    }

    public void openGroceryList(View view) {
//        FIXME: Add grocery list intent after adding activity
    }
}