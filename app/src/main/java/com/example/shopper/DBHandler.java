package com.example.shopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for the db name and version
    public static final String DATABASE_NAME = "shopper.db";
    public static final int DATABASE_VERSION = 1;

    // Initialize constants for the shoppinglist table
    public static final String TABLE_SHOPPPING_LIST ="shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";

    /**
     * Create a Version of our database
     * @param context reference to the activity that initializes a DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates Shopper database tables
     * @param sqLiteDatabase reference to the shopper database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Define create statement for shoppingList table and store it in a string
        String query = "CREATE TABLE " + TABLE_SHOPPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(query);
    }

    /**
     * Creates a new Version of the Shopper database
     * @param sqLiteDatabase reference to the shopper database
     * @param oldVersion old version of the shopper database
     * @param newVersion new version of the shopper database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Define drop statement and store it in a string
        String query = "DROP TABLE IF EXISTS " + TABLE_SHOPPPING_LIST;

        // Execute the drop SQL Statement
        sqLiteDatabase.execSQL(query);

        // Create Tables
        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add button in the Action Bar of the CreateList
     * Activity gets clicked. It inserts a new row into the shopping list table.
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    public void addShoppingList(String name, String store, String date) {
        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // Initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data in to ContentValues Object
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        // Insert data in ContentValues Object into the shoppingList table
        db.insert(TABLE_SHOPPPING_LIST, null, values);

        // Close database reference
        db.close();
    }
}
