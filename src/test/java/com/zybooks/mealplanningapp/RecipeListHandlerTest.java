package com.zybooks.mealplanningapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeListHandlerTest {

    RecipeListHandler recipeListHandler;
    ArrayList<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
        recipes = setupRecipes();
        recipeListHandler = new RecipeListHandler(recipes);
    }

    private ArrayList<Recipe> setupRecipes() {
        return new ArrayList<>(Arrays.asList(
                new Recipe(1, "r1", "i1", "in1", false),
                new Recipe(2, "r2", "i2", "in2", true)
        ));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void nonDeletedRecipes() {
        ArrayList<Recipe> actualNonDeletedRecipes = recipeListHandler.nonDeletedRecipes();
        ArrayList<Recipe> expectedNonDeletedRecipes = recipes
                .stream()
                .filter(recipe -> !recipe.isDeleted())
                .collect(Collectors.toCollection(ArrayList::new));

        assertEquals(expectedNonDeletedRecipes, actualNonDeletedRecipes);

    }

    @Test
    public void getRecipeForID_happyCase() {
        int recipeId = 1;
        // happy case
        Recipe actualRecipe = recipeListHandler.getRecipeForID(recipeId);
        assertEquals(getRecipeForId(recipeId), actualRecipe);

        //recipe not found case
        recipeId = 3;
        actualRecipe = recipeListHandler.getRecipeForID(recipeId);
        assertEquals(getRecipeForId(recipeId), actualRecipe); // should return null

    }

    private Recipe getRecipeForId(int recipeId) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getId() == recipeId)
                .findFirst()
                .orElse(null);
    }
}