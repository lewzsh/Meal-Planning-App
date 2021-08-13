package com.zybooks.mealplanningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GroceryAdapter extends ArrayAdapter<String> {

    public GroceryAdapter(Context context, ArrayList<String> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String ingredient = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_item, parent, false);
        }
        TextView groceryItem = convertView.findViewById(R.id.groceryItemTextView);
        Button deleteButton = convertView.findViewById(R.id.delGroceryItemButton);
        CheckBox groceryCheck = convertView.findViewById(R.id.groceryItemCheckBox);

        groceryItem.setText(ingredient);

        return convertView;

    }

}
