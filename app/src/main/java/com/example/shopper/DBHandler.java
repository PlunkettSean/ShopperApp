package com.example.shopper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for the db name and version
    public static final String DATABASE_NAME = "shopper.db";
    public static final int DATABASE_VERSION = 2;

    // Initialize constants for the shoppinglist table
    public static final String TABLE_SHOPPING_LIST ="shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";

    // Initialize constants for the shoppinglistitem table
    public static final String TABLE_SHOPPING_LIST_ITEM ="shoppinglistitem";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_HAS = "item_has";
    public static final String COLUMN_ITEM_LIST_ID = "list_id";

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
        // Define create statement for shoppinglist table and store it in a string
        String queryListCreate = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(queryListCreate);

        // Define create statement for shoppinglistitem table and store it in a string
        String queryListItemCreate = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(queryListItemCreate);
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
        String queryListUpgrade = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

        // Execute the drop SQL Statement
        sqLiteDatabase.execSQL(queryListUpgrade);

        // Define drop statement and store it in a string
        String queryListItemUpgrade = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM;

        // Execute the drop SQL Statement
        sqLiteDatabase.execSQL(queryListItemUpgrade);

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
        db.insert(TABLE_SHOPPING_LIST, null, values);

        // Close database reference
        db.close();
    }

    /**
     * This method gets called when the add button in the Action Bar of the CreateList
     * Activity gets clicked. It inserts a new row into the shopping list table.
     * @param name shopping list item name
     * @param price shopping list item price
     * @param quantity shopping list item quantity
     * @param listId shopping list id
     */
    public void addItemToList(String name, Double price, Integer quantity, Integer listId) {
        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // Initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data in to ContentValues Object
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_HAS, "false");
        values.put(COLUMN_ITEM_LIST_ID, listId);

        // Insert data in ContentValues Object into the shoppinglistitem table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values);

        // Close database reference
        db.close();
    }

    /**
     * This method gets called when the main activity is created. It will select
     * and return all of the data in the shoppingList table.
     * @return Cursor that contains all the data in the shoppingList table
     */
    public Cursor getShoppingList() {
        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST;

        // execute statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This Method gets called when the ViewList Activity is started
     * @param id shopping list id
     * @return The name of the Shopping list associated with the given id
     */
    public String getShoppingListName(int id) {
        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // Declare and initialize the String that will be returned
        String name = "";

        // Define SELECT statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        // Execute SELECT statement declared above and store it in a Cursor
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);

        // Move to the first row in the Cursor
        cursor.moveToFirst();

        // Check to make sure that name component of Cursor does not equal to null
        if(cursor.getString(cursor.getColumnIndex("name")) != null) {
            // Get the name of the component and store it in String name.
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        // Close the database.
        db.close();

        // Returns the name of the shopping list with the given id.
        return name;
    }
}
