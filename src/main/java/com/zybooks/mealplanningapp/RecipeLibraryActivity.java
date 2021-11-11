package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
public class RecipeLibraryActivity extends AppCompatActivity {

    private RecipeListHandler recipeLibrary;
    private SQLiteManager dbManager;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recipeListView;
    private ArrayList<Recipe> recipeArrayList;
//    add alt text view
    private TextView AltText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        initWidgets();
        setRecipeAdapter();
    }

    private void initWidgets() {
        recipeListView = findViewById(R.id.recipeListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecipeLibraryActivity.this, RecyclerView.VERTICAL, false);
        recipeListView.setLayoutManager(linearLayoutManager);
    }

    private void setRecipeAdapter() {
        dbManager = SQLiteManager.instanceOfDatabase(this);
        recipeArrayList = dbManager.populateRecipeListArray();
        recipeLibrary = new RecipeListHandler(recipeArrayList);
//show alternate text starts here:
        if (recipeLibrary == null) {
            AltText = findViewById(R.id.altText);
        } else {
        recipeAdapter = new RecipeAdapter(RecipeLibraryActivity.this, recipeLibrary.nonDeletedRecipes());
        recipeListView.setAdapter(recipeAdapter);
        }
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
