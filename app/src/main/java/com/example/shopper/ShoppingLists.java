package com.example.shopper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * The ShoppingList class will map the data selected from the shopping list table, in the cursor,
 * to the li_shopping_list layout resource.
 */
public class ShoppingLists extends CursorAdapter {

    /**
     * Initialize a ShoppingList CursorAdapter
     *
     * @param context Reference to the Activity that initializes the ShoppingLists
     *                CursorAdaptor
     * @param c       Reference to the cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public ShoppingLists(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Reference to the Activity that initializes the ShoppingLists
     *                CursorAdaptor
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  Reference to the shoppingListView that will contain the new
     *                View created by this method.
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, parent, false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Reference to the Activity that initializes the ShoppingLists
     *                CursorAdaptor
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /**
         * Set the value of nameTextView of li_shopping_list layout to the name of the
         * ShoppingList from the cursor
         */
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        /**
         * Set the value of storeTextView of li_shopping_list layout to the name of the
         * ShoppingList from the cursor
         */
        ((TextView) view.findViewById(R.id.storeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("store")));
        /**
         * Set the value of dateTextView of li_shopping_list layout to the name of the
         * ShoppingList from the cursor
         */
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));
    }
}
