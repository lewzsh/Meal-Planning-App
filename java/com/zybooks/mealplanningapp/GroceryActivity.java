package com.zybooks.mealplanningapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryActivity extends AppCompatActivity {

    private final String KEY_SAVED_LIST = "currentListItems";

    private GroceryAdapter itemsAdapter;
    private ArrayList<String> items;

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

        // Restore state
        if (savedInstanceState != null) {
            items = savedInstanceState.getStringArrayList(KEY_SAVED_LIST);
        }
        else {
            items = new ArrayList<>();
        }

        setGroceryAdapter();
        SetUpListViewListener();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_SAVED_LIST, items);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        items = savedInstanceState.getStringArrayList(KEY_SAVED_LIST);
        setGroceryAdapter();
    }

    public void setGroceryAdapter() {
        itemsAdapter = new GroceryAdapter(this, items);
        groceryListView.setAdapter(itemsAdapter);
    }

    // FIXME: change to use delete button, not long click
    private void SetUpListViewListener() {
        groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
                //Since we're using the long text adapter we need to return a boolean and return true

            }
        });
    }

    private void addItem(View view) {

        String itemText = userInput.getText().toString();

        if(!(itemText.equals(""))) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged();
            userInput.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter text...", Toast.LENGTH_LONG);
        }

    }
}