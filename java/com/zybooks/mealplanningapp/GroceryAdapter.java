package com.zybooks.mealplanningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GroceryAdapter extends ArrayAdapter<String> {

    private ArrayList<String> ingredients;
    private Context context;
    private DelBtnClickListener delClkListener = null;

    public GroceryAdapter(Context context, ArrayList<String> ingredients, DelBtnClickListener listener) {
        super(context, 0, ingredients);
        delClkListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String ingredient = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_item, parent, false);
        }
        TextView groceryItemText = convertView.findViewById(R.id.groceryItemTextView);
        Button deleteButton = convertView.findViewById(R.id.delGroceryItemButton);
        deleteButton.setTag(position);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delClkListener != null) {
                    delClkListener.onBtnClick((Integer) v.getTag());
                }
            }
        });
        groceryItemText.setText(ingredient);

        return convertView;

    }


}
