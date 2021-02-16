package com.example.shopper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ViewList extends AppCompatActivity {

    // Declare a Bundle and a long used to get and store the data sent from the MainActivity
    Bundle bundle;
    long id;

    // Declare a DBHandler
    DBHandler dbHandler;

    /**
     * This method initializes the Action Bar and View of the activity.
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the bundle
        bundle = this.getIntent().getExtras();

        // Use Bundle to get the id and store it into the id field
        id = bundle.getLong("_id");

        // Initialize the DBHandler
        dbHandler =  new DBHandler(this, null);

        // Call getShoppingListName and store it into a new String
        String shoppingListName = dbHandler.getShoppingListName((int) id);

        // Set the title of the ViewList Activity to shoppingListName
        setTitle(shoppingListName);
    }

    /**
     *
     * @param view view
     */
    public void openAddItem(View view) {
    }
}