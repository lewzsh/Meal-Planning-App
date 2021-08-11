package com.zybooks.mealplanningapp;

import java.util.ArrayList;
import java.util.Date;

public class Recipe {

    public static String RECIPE_EDIT_ID = "recipe_id";

    private int id;
    private String title;
    private String ingredients;
    private String instructions;
    private boolean deleted;

    public Recipe(int id, String title, String ingredients, String instructions, boolean deleted) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.deleted = deleted;
    }

    public Recipe(int id, String title, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.deleted = false;
    }

    public int getId() {
        return id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
