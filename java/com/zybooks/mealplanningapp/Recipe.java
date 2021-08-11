package com.zybooks.mealplanningapp;

import java.util.ArrayList;
import java.util.Date;

public class Recipe {

    public static ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    public static String RECIPE_EDIT_EXTRA = "recipeEdit";

    private int id;
    private String title;
    private String ingredients;
    private String instructions;
    private Date datePlanned;
    private boolean deleted;

    public Recipe(int id, String title, String ingredients, String instructions, Date datePlanned, boolean deleted) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.datePlanned = datePlanned;
        this.deleted = deleted;
    }

    public Recipe(int id, String title, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.datePlanned = null;
        this.deleted = false;
    }

    public Recipe(int id, String title, String ingredients, String instructions, Date datePlanned) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.datePlanned = datePlanned;
        this.deleted = false;
    }

    public Recipe(int id, String title, String ingredients, String instructions, boolean deleted) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.datePlanned = null;
        this.deleted = deleted;
    }

    public static Recipe getRecipeForID(int passedRecipeId) {
        for (Recipe recipe : recipeArrayList) {
            if (recipe.getId() == passedRecipeId) {
                return recipe;
            }
        }
        return null;
    }

    public static ArrayList<Recipe> nonDeletedRecipes() {
        ArrayList<Recipe> nonDeleted = new ArrayList<>();
        for (Recipe recipe : recipeArrayList) {
            if (!recipe.isDeleted()) {
                nonDeleted.add(recipe);
            }
        }
        return nonDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDatePlanned() {
        return datePlanned;
    }

    public void setDatePlanned(Date datePlanned) {
        this.datePlanned = datePlanned;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
