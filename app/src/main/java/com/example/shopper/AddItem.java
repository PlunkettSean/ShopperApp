package com.example.shopper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Declare a Bundle and a long used to get and store the data sent from the ViewList
    Bundle bundle;
    long id;

    // Declare a DBHandler
    DBHandler dbHandler;

    // Declare intent
    Intent intent;

    // Derclare EditTexts
    EditText nameEditText;
    EditText priceEditText;

    // declare spinner
    Spinner quantitySpinner;

    // declare a String to store the selected Spinner Value
    String quantity;

    /**
     * This method initializes the Action Bar and View of the activity.
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the bundle
        bundle = this.getIntent().getExtras();

        // Use Bundle to get the id and store it into the id field
        id = bundle.getLong("_id");

        // Initialize the DBHandler
        dbHandler =  new DBHandler(this, null);

        // Initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        // Initialize Spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        // Initialize ArrayAdapter with values in quantities String-Array
        // and stylize it with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);

        // further stylize the Array Adapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Set the ArrayAdapter on Spinner
        quantitySpinner.setAdapter(adapter);

        // Register an OnItemSelectListener to the Spinner
        quantitySpinner.setOnItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
            case R.id.action_view_list :
                // initialize an Intent for the ViewList Activity and start it.
                intent = new Intent(this, ViewList.class);
                // put the database id into the intent
                intent.putExtra("_id", id);
                startActivity(intent);
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add Button in action bar is selected.
     * @param menuItem menuItem
     */
    public void addItem(MenuItem menuItem) {
        // get data in EditTexts and store it in strings
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        // trim Strings ans see if they're equal to empy strings
        if (name.trim().equals("") || price.trim().equals("") || quantity.trim().equals("")) {
            // Display a Toast "Please Enter Name, Price and Quantity." when any var is empty
            Toast.makeText(this, "Please Enter Name, Price and Quantity.",
                    Toast.LENGTH_LONG).show();
        } else {
            // add item into the database
            dbHandler.addItemToList(name, Double.parseDouble(price), Integer.parseInt(quantity), (int) id);
            // display Toast saying "Item Added!" when all var are non-Nul or empty
            Toast.makeText(this, "Item Added!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity = parent.getItemAtPosition(position).toString();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}