package com.example.shopper;

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

    }

    /**
     * Creates a new Version of the Shopper database
     * @param sqLiteDatabase reference to the shopper database
     * @param oldVersion old version of the shopper database
     * @param newVersion new version of the shopper database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
