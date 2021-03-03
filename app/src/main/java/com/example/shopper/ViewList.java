package com.example.shopper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ViewList extends AppCompatActivity {

    // Declare a Bundle and a long used to get and store the data sent from the MainActivity
    Bundle bundle;
    long id;

    // Declare a DBHandler
    DBHandler dbHandler;

    // declare intent
    Intent intent;

    // Declare a ShoppingListItems CursorAdaptor
    ShoppingListItems shoppingListItemsAdapter;

    // Declare a ListView
    ListView itemListView;

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

        // Initialize itemListView
        itemListView = (ListView) findViewById(R.id.itemsListView);

        //initialize the ShoppingListItemsCursorAdaptor
        shoppingListItemsAdapter = new ShoppingListItems(this,dbHandler.getShoppingListItems((int)id), 0);

        // Set the ShoppingListItems CursorAdaptor (shoppingListItemsAdapter) on the itemListView
        itemListView.setAdapter(shoppingListItemsAdapter);

        // register an OnItemClickListener on the ListView
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * This method gets called when an item in the ListView is clicked
             * @param parent itemListView
             * @param view ViewList activity view
             * @param position posistion of the clicked item
             * @param id database id of the clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // call method that updates the clicked item item_has to true if it is false
                updateItem(id);
            }
        });
    }

    /**
     * This method gets called when an item is clicked in the ListView.
     * It Updates the clicked item's item_has to true if it's false.
     * @param id database id of the clicked item
     */
    private void updateItem(long id) {
        // checking if the clicked item is unpurchaced
        if (dbHandler.isItemUnpurchased((int) id) == 1) {
            // make clicked item purchased
            dbHandler.updateItem((int) id);

            // refresh ListView updated data
            shoppingListItemsAdapter.swapCursor(dbHandler.getShoppingListItems((int) this.id));
            shoppingListItemsAdapter.notifyDataSetChanged();

            //display Toast indicating item is purchased
            Toast.makeText(this, "item Purchased!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method further initializes the Action Bar of the activity.
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar.
     * @param menu menu resource file for the activity.
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is selected and it
     * controls what happens when the menu item is selected.
     * @param item selected item in the overflow menu.
     * @return true if the menu item is selected, else false.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the menu item selected
        switch(item.getItemId()) {
            case R.id.action_home :
                // initialize an Intent for the MainActivity and start it.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                // initialize an Intent for the CreateList Activity and start it.
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_add_item :
                // initialize an Intent for the AddItem Activity and start it.
                intent = new Intent(this, AddItem.class);
                // put the database id into the intent
                intent.putExtra("_id", id);
                startActivity(intent);
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add Floating Action Button is selected.
     * It starts the AddItem Activity
     * @param view view
     */
    public void openAddItem(View view) {
        // initialize an Intent for the CreateList Activity and start it.
        intent = new Intent(this, AddItem.class);
        // put the database id into the intent
        intent.putExtra("_id", id);
        startActivity(intent);
    }
}