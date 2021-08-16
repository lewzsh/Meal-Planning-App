package com.zybooks.mealplanningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "RecipeDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Recipe";

    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String INGREDIENTS_FIELD = "ingredients";
    private static final String INSTRUCTIONS_FIELD = "instructions";
    private static final String DELETED_FIELD = "deleted";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(INGREDIENTS_FIELD)
                .append(" TEXT, ")
                .append(INSTRUCTIONS_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" INT)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecipeToDatabase(String title, String ingredients, String instructions) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues contentValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair
        contentValues.put(TITLE_FIELD, title);
        contentValues.put(INGREDIENTS_FIELD, ingredients);
        contentValues.put(INSTRUCTIONS_FIELD, instructions);
        contentValues.put(DELETED_FIELD, 0);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, contentValues);

        // at last we are closing our database.
        db.close();
    }

    public void updateRecipeInDB(int id, String title, String ingredients, String instructions, int deleted) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE_FIELD, title);
        contentValues.put(INGREDIENTS_FIELD, ingredients);
        contentValues.put(INSTRUCTIONS_FIELD, instructions);
        contentValues.put(DELETED_FIELD, deleted);

        db.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{ String.valueOf(id) });
        db.close();
    }

    public ArrayList<Recipe> populateRecipeListArray() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME;
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
