package com.zybooks.mealplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroceryActivity extends AppCompatActivity {

    private GroceryAdapter itemsAdapter;
    private ArrayList<String> items;
    private SharedPreferences savedGroceryListSP;

    private EditText userInput;
    private ListView groceryListView;
    private Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        groceryListView = findViewById(R.id.groceryListView);
        addItemButton = findViewById(R.id.addItemButton);
        userInput = findViewById(R.id.groceryItemEditText);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<>();
    }

    private void checkForRecipeExtra() {
        Intent previousIntent = getIntent();

        String passedIngredients = previousIntent.getStringExtra("INGREDIENTS");

        if (passedIngredients != null) {
            ArrayList<String> ingredientsToAdd = convertStrToAL(passedIngredients, "\n");
            for (String ingredient : ingredientsToAdd) {
                items.add(ingredient);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        savedGroceryListSP = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savedGroceryListSP.edit();

        String saveString;
        if (items.size() > 0) {
            saveString = convertALToString(items, "\n").trim();
        }
        else {
            saveString = null;
        }
        editor.putString("listItems", saveString);
        editor.apply();
    }

    @Override
    protected void onResume() {

        super.onResume();
        // Import saved values
        savedGroceryListSP = getPreferences(Context.MODE_PRIVATE);
        String strList = savedGroceryListSP.getString("listItems", null);

        if (strList != null) {
            this.items = convertStrToAL(strList, "\n");
        }

        checkForRecipeExtra();
        setGroceryAdapter();

    }

    public void setGroceryAdapter() {
        itemsAdapter = new GroceryAdapter(this, items, new DelBtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                deleteItem(position);
            }
        });
        groceryListView.setAdapter(itemsAdapter);
    }

    public void deleteItem(int ingredientPosID) {
        items.remove(ingredientPosID);
        itemsAdapter.notifyDataSetChanged();

        Context context = getApplicationContext();
        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
    }

    private void addItem(View view) {

        String itemText = userInput.getText().toString();

        if(!(itemText.equals(""))) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged();
            userInput.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter text...", Toast.LENGTH_LONG).show();
        }

    }

    public void goBackToMenu(View view) {
        Intent goToMenu = new Intent(this, MainActivity.class);
        startActivity(goToMenu);
    }

    public String convertALToString(ArrayList<String> givenArrayList, String delimiter) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < givenArrayList.size(); ++i) {
            str.append(givenArrayList.get(i) + delimiter);
        }
        String newString = str.toString();

        return newString;
    }

    public ArrayList<String> convertStrToAL(String givenString, String delimiter) {
        String[] strSplit = givenString.split(delimiter);
        List<String> fixedList = Arrays.asList(strSplit);
        ArrayList<String> newArrayList = new ArrayList<>(fixedList);

        return newArrayList;
    }

}