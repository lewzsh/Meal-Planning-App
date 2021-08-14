package com.zybooks.mealplanningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.zybooks.mealplanningapp.utils.SqlTableUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SQLiteManagerTest {

    @Mock
    SQLiteDatabase sqLiteDatabase;

    @Mock
    Context mockContext;

    @Mock
    ContentValues mockContentValues;

    SQLiteManager sqLiteManager;

    SQLiteManager spySqlLiteManager;

    ArrayList<Recipe> recipeArrayList;

    @Mock
    Cursor mockCursor;

    @Before
    public void setUp() throws Exception {
        sqLiteManager = new SQLiteManager(mockContext);
        spySqlLiteManager = spy(sqLiteManager);

        doReturn(sqLiteDatabase).when(spySqlLiteManager).getReadableDatabase();
        doReturn(sqLiteDatabase).when(spySqlLiteManager).getWritableDatabase();
        recipeArrayList = setupRecipes();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void instanceOfDatabase() {
    }

    @Test
    public void onCreate() {
        spySqlLiteManager.onCreate(sqLiteDatabase);
        verify(sqLiteDatabase,
                times(1))
                .execSQL(createRecipeTableSqlString());
    }

    @Test
    public void onUpgrade() {
        spySqlLiteManager.onUpgrade(sqLiteDatabase, 0, 1);
        verify(sqLiteDatabase, times(1)).execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        verify(sqLiteDatabase,
                times(1))
                .execSQL(createRecipeTableSqlString());
    }

    @Test
    public void addRecipeToDatabase() {
        String title = "t1";
        String ingredients = "i1";
        String instructions = "in1";

        doReturn(mockContentValues).when(spySqlLiteManager).getContentValues(title, ingredients, instructions, 0);

        spySqlLiteManager.addRecipeToDatabase(title, ingredients, instructions);
        verify(sqLiteDatabase, times(1)).insert(RECIPE_TABLE_NAME, null, mockContentValues);

        verify(sqLiteDatabase, times(1)).close();

    }

    @Test
    public void updateRecipeInDB() {
        int id = 1;
        String title = "t1";
        String ingredients = "i1";
        String instructions = "in1";
        int deleted = 0;

        doReturn(mockContentValues).when(spySqlLiteManager).getContentValues(title, ingredients, instructions, deleted);

        spySqlLiteManager.updateRecipeInDB(1, title, ingredients, instructions,deleted);
        verify(sqLiteDatabase, times(1)).update
                        (
                        RECIPE_TABLE_NAME,
                        mockContentValues,
                        RECIPE_ID_FIELD + " =? ",
                        new String[]{ String.valueOf(id) }
                        );

        verify(sqLiteDatabase, times(1)).close();
    }


    private ArrayList<Recipe> setupRecipes() {
        return new ArrayList<>(Arrays.asList(
                new Recipe(1, "r1", "i1", "in1", false)
        ));
    }

    @Test
    public void populateRecipeListArray() {
        String sql = "SELECT * FROM " + RECIPE_TABLE_NAME;
        when(sqLiteDatabase.rawQuery(sql,null)).thenReturn(mockCursor);
        when(mockCursor.moveToFirst()).thenReturn(true);

        when(mockCursor.getInt(0)).thenReturn(1);
        when(mockCursor.getString(1)).thenReturn("r1");
        when(mockCursor.getString(2)).thenReturn("i1");
        when(mockCursor.getString(3)).thenReturn("in1");
        when(mockCursor.getInt(4)).thenReturn(0);

        when(mockCursor.moveToNext()).thenReturn(false);

        ArrayList<Recipe> actualRecipeArrayList = spySqlLiteManager.populateRecipeListArray();

        verify(mockCursor, times(1)).close();

        assertEquals(recipeArrayList, actualRecipeArrayList);
    }
}