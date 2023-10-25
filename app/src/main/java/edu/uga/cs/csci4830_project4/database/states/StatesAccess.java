package edu.uga.cs.csci4830_project4.database.states;

import static edu.uga.cs.csci4830_project4.database.utils.UtilMethods.getColumnIndex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides access to the state_capitals table in the database and
 * encapsulates the CRUD (Create, Read, Update, Delete) operations for state capital data.
 */
public class StatesAccess {

    private final SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    /**
     * Constructs a new StatesAccess instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public StatesAccess(Context context) {
        helper = StatesDatabaseHelper.getInstance(context);
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
     * @param model The StateModel representing the state capital to store.
     * @return The stored StateModel with its assigned ID.
     */
    public StateModel storeState(StateModel model) {
        ContentValues values = new ContentValues();
        values.put(StatesDatabaseHelper.COLUMN_STATE_NAME, model.getStateName());
        values.put(StatesDatabaseHelper.COLUMN_CAPITAL_CITY, model.getCapitalCity());
        values.put(StatesDatabaseHelper.COLUMN_SECOND_CITY, model.getSecondCity());
        values.put(StatesDatabaseHelper.COLUMN_THIRD_CITY, model.getThirdCity());
        values.put(StatesDatabaseHelper.COLUMN_STATEHOOD, model.getStatehood());
        values.put(StatesDatabaseHelper.COLUMN_CAPITAL_SINCE, model.getCapitalSince());
        values.put(StatesDatabaseHelper.COLUMN_SIZE_RANK, model.getSizeRank());

        long id = db.insert(StatesDatabaseHelper.TABLE_NAME, null, values);
        model.setId(id);

        return model;
    }

    /**
     * Retrieves from the database a list of all {@link StateModel} objects.
     *
     * @return A list of state capital model objects representing entries in the database.
     */
    public List<StateModel> retrieveAllStates() {
        List<StateModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(StatesDatabaseHelper.TABLE_NAME, null, null, null,
                null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_ID));
                    String state = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_STATE_NAME));
                    String capitalCity = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_CAPITAL_CITY));
                    String secondCity = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_SECOND_CITY));
                    String thirdCity = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_THIRD_CITY));
                    String statehood = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_STATEHOOD));
                    String capitalSince = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_CAPITAL_SINCE));
                    String sizeRank = cursor.getString(getColumnIndex(cursor,
                            StatesDatabaseHelper.COLUMN_SIZE_RANK));

                    StateModel model = new StateModel();
                    model.setId(id);
                    model.setStateName(state);
                    model.setCapitalCity(capitalCity);
                    model.setSecondCity(secondCity);
                    model.setThirdCity(thirdCity);
                    model.setStatehood(statehood);
                    model.setCapitalSince(capitalSince);
                    model.setSizeRank(sizeRank);

                    models.add(model);
                } while (cursor.moveToNext());
            }
        }

        return models;
    }
}
