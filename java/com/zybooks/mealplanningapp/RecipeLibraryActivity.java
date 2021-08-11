package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RecipeLibraryActivity extends AppCompatActivity {

    private ListView recipeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initWidgets();
        setRecipeAdapter();
        setOnClickListener();
    }

    private void initWidgets() {
        recipeListView = findViewById(R.id.recipeListView);
    }

    private void setRecipeAdapter() {
        RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), Recipe.nonDeletedRecipes());
        recipeListView.setAdapter(recipeAdapter);
    }

    private void setOnClickListener() {
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = (Recipe) recipeListView.getItemAtPosition(position);
                Intent viewRecipeIntent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                viewRecipeIntent.putExtra(Recipe.RECIPE_EDIT_EXTRA, selectedRecipe.getId());
                startActivity(viewRecipeIntent);
            }
        });
    }

    public void newRecipe(View view) {
        Intent newRecipeIntent = new Intent(this, RecipeEditActivity.class);
        startActivity(newRecipeIntent);
    }

    public void goToMenu(View view) {
        Intent goToMenu = new Intent(this, MainActivity.class);
        startActivity(goToMenu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecipeAdapter();
    }
}