package com.zybooks.mealplanningapp;

import java.util.ArrayList;

public class RecipeListHandler {

    private ArrayList<Recipe> recipeArrayList;

    public RecipeListHandler(ArrayList<Recipe> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }

    public ArrayList<Recipe> nonDeletedRecipes() {
        ArrayList<Recipe> nonDeleted = new ArrayList<>();
        for (Recipe recipe : recipeArrayList) {
            if (!recipe.isDeleted()) {
                nonDeleted.add(recipe);
            }
        }
        return nonDeleted;
    }

    public Recipe getRecipeForID(int passedRecipeId) {
        for (Recipe recipe : recipeArrayList) {
            if (recipe.getId() == passedRecipeId) {
                return recipe;
            }
        }
        return null;
    }
}
