package com.zybooks.mealplanningapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "RecipeDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Recipe";
    private static final String COUNTER = "counter";

    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String INGREDIENTS_FIELD = "ingredients";
    private static final String INSTRUCTIONS_FIELD = "instructions";
    private static final String DATE_FIELD = "datePlanned";
    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

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
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(INGREDIENTS_FIELD)
                .append(" TEXT, ")
                .append(INSTRUCTIONS_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" INT)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
    }

    public void addRecipeToDatabase(Recipe recipe) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues contentValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair
        // We get the value from the passed object
        contentValues.put(ID_FIELD, recipe.getId());
        contentValues.put(TITLE_FIELD, recipe.getTitle());
        contentValues.put(INGREDIENTS_FIELD, recipe.getIngredients());
        contentValues.put(INSTRUCTIONS_FIELD, recipe.getInstructions());
        contentValues.put(DATE_FIELD, getStringFromDate(recipe.getDatePlanned()));
        contentValues.put(DELETED_FIELD, getNumberFromBoolean(recipe.isDeleted()));

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, contentValues);

        // at last we are closing our database.
        db.close();
    }

    public void populateRecipeListArray() {
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String ingredients = result.getString(3);
                    String instructions = result.getString(4);
                    String datePlannedString = result.getString(5);
                    int deletedInt = result.getInt(6);

                    Date datePlanned = getDateFromString(datePlannedString);
                    boolean deleted = getBoolFromNum(deletedInt);

                    Recipe recipe = new Recipe(id, title, ingredients, instructions, datePlanned, deleted);
                    Recipe.recipeArrayList.add(recipe);
                }
            }
        }


    }

    public void updateRecipeInDB(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, recipe.getId());
        contentValues.put(TITLE_FIELD, recipe.getTitle());
        contentValues.put(INGREDIENTS_FIELD, recipe.getIngredients());
        contentValues.put(INSTRUCTIONS_FIELD, recipe.getInstructions());
        contentValues.put(DATE_FIELD, getStringFromDate(recipe.getDatePlanned()));
        contentValues.put(DELETED_FIELD, getNumberFromBoolean(recipe.isDeleted()));

        db.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{ String.valueOf(recipe.getId()) });
        db.close();
    }

    // Helper conversion methods
    private int getNumberFromBoolean(boolean deleted) {
        if (deleted) {
            return 1;
        }
        else {
            return 0;
        }
    }

    private boolean getBoolFromNum(int deletedInt) {
        if (deletedInt == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    private String getStringFromDate(Date datePlanned) {
        if (datePlanned == null) {
            return null;
        }
        return dateFormat.format(datePlanned);
    }

    private Date getDateFromString(String datestring) {
        try {
            return dateFormat.parse(datestring);
        }
        catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}
