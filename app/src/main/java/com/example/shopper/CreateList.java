package com.example.shopper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateList extends AppCompatActivity {
    // declare intent
    Intent intent;

    // declare EditText;
    EditText nameEditText;
    EditText storeEditText;
    EditText dateEditText;

    // declare Calender
    Calendar calendar;

    // Declare a DBHandler
    DBHandler dbHandler;

    /**
     * This method initializes the Action Bar and View of the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize EditTexts
        nameEditText = findViewById(R.id.nameEditText);
        storeEditText = findViewById(R.id.storeEditText);
        dateEditText = findViewById(R.id.dateEditText);

        // initialize calender
        calendar = Calendar.getInstance();

        // initialize DatePickerDialog and register an OnDateSetListener to it.
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            /**
             * This method handles the OnDateDet event
             * @param datePicker DatePickerDialog view
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // set the year, month, and dayOfMonth selected in the dialog into the calender
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // call method that takes date in calendar and place it in dateEditText
                updateDueDate();
            }
        };
        // register OnCLickListener to the dateEditText
        dateEditText.setOnClickListener(new View.OnClickListener() {
            /**
             * this method is called every time the dateEditText is clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                //display DatePickerDialog with current Date selected
                new DatePickerDialog(CreateList.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);
    }

    private void updateDueDate() {
        // create a format for the date in the calendar
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // apply format to date in calender and set formatted date in dateEditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is selected and it
     * controls what happens when the menu item is selected.
     * @param item selected item in the overflow menu.
     * @return true if the menu item is selected, else false.
     */
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
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked
     * @param menuItem add list menu item
     */
    public void createList(MenuItem menuItem) {
        // get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String store = storeEditText.getText().toString();
        String date = dateEditText.getText().toString();

        // trim Strings and check to see if they're equal to an empty string
        if(name.trim().equals("") || store.trim().equals("") || date.trim().equals("")) {
            //display "Please enter a name, store, and date!"
            Toast.makeText(this, "Please enter a name, store, and date!",
                    Toast.LENGTH_LONG).show();
        } else {
            // add shopping list into database
            dbHandler.addShoppingList(name, store, date);

            // display "Shopping List Created!"
            Toast.makeText(this, "Shopping List Created!",
                    Toast.LENGTH_LONG).show();
        }
    }
}