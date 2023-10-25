package edu.uga.cs.csci4830_project4.database.access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.database.StateCapitalsDatabaseHelper;
import edu.uga.cs.csci4830_project4.database.model.StateCapitalModel;

/**
 * This class provides access to the state_capitals table in the database and
 * encapsulates the CRUD (Create, Read, Update, Delete) operations for state capital data.
 */
public class StateCapitalsAccess {

    private final SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    /**
     * Constructs a new StateCapitalsAccess instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public StateCapitalsAccess(Context context) {
        helper = StateCapitalsDatabaseHelper.getInstance(context);
    }

    /**
     * Opens the database for write access.
     */
    public void open() {
        db = helper.getWritableDatabase();
    }

    /**
     * Closes the database.
     */
    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

    /**
     * Stores a state capital record in the database and returns the model with its assigned ID.
     *
     * @param model The StateCapitalModel representing the state capital to store.
     * @return The stored StateCapitalModel with its assigned ID.
     */
    public StateCapitalModel storeStateCapital(StateCapitalModel model) {
        ContentValues values = new ContentValues();
        values.put(StateCapitalsDatabaseHelper.COLUMN_STATE, model.getState());
        values.put(StateCapitalsDatabaseHelper.COLUMN_CHOICES, model.getChoices());

        long id = db.insert(StateCapitalsDatabaseHelper.TABLE_NAME, null, values);
        model.setId(id);

        return model;
    }

    /**
     * Retrieves from the database a list of all {@link StateCapitalModel} objects.
     *
     * @return A list of state capital model objects representing entries in the database.
     */
    public List<StateCapitalModel> retrieveAllStateCapitals() {
        List<StateCapitalModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(StateCapitalsDatabaseHelper.TABLE_NAME, null, null, null,
                null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(getColumnIndex(cursor,
                            StateCapitalsDatabaseHelper.COLUMN_ID));
                    String state = cursor.getString(getColumnIndex(cursor,
                            StateCapitalsDatabaseHelper.COLUMN_STATE));
                    String choices = cursor.getString(getColumnIndex(cursor,
                            StateCapitalsDatabaseHelper.COLUMN_CHOICES));

                    StateCapitalModel model = new StateCapitalModel();
                    model.setId(id);
                    model.setState(state);
                    model.setChoices(choices);

                    models.add(model);
                } while (cursor.moveToNext());
            }
        }

        return models;
    }

    /**
     * Retrieves the column index for the given column name in a cursor.
     *
     * @param cursor The cursor from which to retrieve the column index.
     * @param column The name of the column for which to find the index.
     * @return The column index within the cursor.
     */
    private int getColumnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }
}
