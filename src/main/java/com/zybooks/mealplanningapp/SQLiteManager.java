package com.zybooks.mealplanningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.zybooks.mealplanningapp.utils.SqlTableUtils;
import static com.zybooks.mealplanningapp.utils.SqlTableUtils.*;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    public SQLiteManager(Context context) {
        super(context, RECIPE_DATABASE_NAME, null, RECIPE_DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createRecipeTableSqlString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        onCreate(db);
    }

    ContentValues getContentValues(String title, String ingredients, String instructions, int deleted) {
        // on below line we are creating a
        // variable for content values.
        ContentValues contentValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair
        contentValues.put(RECIPE_TITLE_FIELD, title);
        contentValues.put(RECIPE_INGREDIENTS_FIELD, ingredients);
        contentValues.put(RECIPE_INSTRUCTIONS_FIELD, instructions);
        contentValues.put(RECIPE_DELETED_FIELD, deleted);
        return contentValues;
    }
  
    public void addRecipeToDatabase(String title, String ingredients, String instructions) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValues(title, ingredients, instructions, 0);

        // after adding all values we are passing
        // content values to our table.
        db.insert(RECIPE_TABLE_NAME, null, contentValues);

        // at last we are closing our database.
        db.close();
    }

    public void updateRecipeInDB(int id, String title, String ingredients, String instructions, int deleted) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValues(title, ingredients, instructions, deleted);

        db.update(RECIPE_TABLE_NAME, contentValues, RECIPE_ID_FIELD + " =? ", new String[]{ String.valueOf(id) });
        db.close();
    }

    public ArrayList<Recipe> populateRecipeListArray() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        String sql = "SELECT * FROM " + RECIPE_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String ingredients = cursor.getString(2);
                String instructions = cursor.getString(3);
                int deletedInt = cursor.getInt(4);

                boolean deleted = getBoolFromNum(deletedInt);

                Recipe recipe = new Recipe(id, title, ingredients, instructions, deleted);

                recipeArrayList.add(recipe);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return recipeArrayList;

    }


    // Helper conversion methods

    private boolean getBoolFromNum(int deletedInt) {
        if (deletedInt == 1) {
            return true;
        } else {
            return false;
        }
    }
}
