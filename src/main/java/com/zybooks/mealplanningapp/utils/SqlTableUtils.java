package com.zybooks.mealplanningapp.utils;

public class SqlTableUtils {
    public static final String RECIPE_DATABASE_NAME = "RecipeDB";
    public static final int RECIPE_DATABASE_VERSION = 1;
    public static final String RECIPE_TABLE_NAME = "Recipe";

    public static final String RECIPE_ID_FIELD = "id";
    public static final String RECIPE_TITLE_FIELD = "title";
    public static final String RECIPE_INGREDIENTS_FIELD = "ingredients";
    public static final String RECIPE_INSTRUCTIONS_FIELD = "instructions";
    public static final String RECIPE_DELETED_FIELD = "deleted";

    public static String createRecipeTableSqlString() {
        StringBuilder sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(RECIPE_TABLE_NAME)
                .append("(")
                .append(RECIPE_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(RECIPE_TITLE_FIELD)
                .append(" TEXT, ")
                .append(RECIPE_INGREDIENTS_FIELD)
                .append(" TEXT, ")
                .append(RECIPE_INSTRUCTIONS_FIELD)
                .append(" TEXT, ")
                .append(RECIPE_DELETED_FIELD)
                .append(" INT)");
        return sql.toString();
    }
}
