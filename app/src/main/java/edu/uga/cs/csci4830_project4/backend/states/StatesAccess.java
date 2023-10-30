package edu.uga.cs.csci4830_project4.backend.states;

import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_CAPITAL_CITY;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_CAPITAL_SINCE;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_ID;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_SECOND_CITY;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_SIZE_RANK;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_STATEHOOD;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_STATE_NAME;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.COLUMN_THIRD_CITY;
import static edu.uga.cs.csci4830_project4.backend.states.StateTableValues.TABLE_NAME;
import static edu.uga.cs.csci4830_project4.backend.utils.BackendUtilMethods.getColumnIndex;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabaseHelper;

/**
 * This class provides access to the state_capitals table in the database and
 * encapsulates the CRUD (Create, Read, Update, Delete) operations for state capital data.
 */
public class StatesAccess implements IAccess<StateModel> {

    private final IDatabaseHelper helper;
    private @Nullable IDatabase db;

    /**
     * Constructs a new StatesAccess instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public StatesAccess(Context context) {
        helper = StatesDatabaseHelper.getInstance(context);
    }

    /**
     * Package-private constructor meant only for testing, DO NOT USE IN PRODUCTION.
     *
     * @param helper the database helper.
     */
    StatesAccess(IDatabaseHelper helper) {
        this.helper = helper;
    }

    /**
     * Returns a list of unique random states from the database.
     *
     * @param amount the amount of states to return.
     * @return a list of unique random states from the database.
     */
    public List<StateModel> getRandomStates(int amount) {
        List<StateModel> states = retrieveAll();
        if (states == null || amount > states.size()) {
            int size = states == null ? 0 : states.size();
            String message = String.format(Locale.getDefault(), "Not enough states in the " +
                    "database for specified amount %d. There are currently %d states in the " +
                    "database.", amount, size);
            throw new IllegalStateException(message);
        }
        Collections.shuffle(states);
        return states.subList(0, amount);
    }

    @Override
    public void open() {
        db = helper.getModifiableDatabase();
    }

    @Override
    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    public StateModel store(StateModel model) {
        if (db == null) {
            return null;
        }

        Map<String, Object> values = new HashMap<>();
        values.put(COLUMN_STATE_NAME, model.getStateName());
        values.put(COLUMN_CAPITAL_CITY, model.getCapitalCity());
        values.put(COLUMN_SECOND_CITY, model.getSecondCity());
        values.put(COLUMN_THIRD_CITY, model.getThirdCity());
        values.put(COLUMN_STATEHOOD, model.getStatehood());
        values.put(COLUMN_CAPITAL_SINCE, model.getCapitalSince());
        values.put(COLUMN_SIZE_RANK, model.getSizeRank());

        long id = db.insert(TABLE_NAME, null, values);
        model.setId(id);

        return model;
    }

    @Override
    public @Nullable StateModel getById(long id) {
        List<StateModel> models = retrieve(null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        return models == null || models.isEmpty() ? null : models.get(0);
    }

    /**
     * Returns the first found state model with the specified state name.
     *
     * @param stateName the state name.
     * @return the first found state model with the specified state name.
     */
    public StateModel getByStateName(String stateName) {
        List<StateModel> models = retrieve(null, "state_name = ?",
                new String[]{stateName}, null, null, null, null);
        return models == null || models.isEmpty() ? null : models.get(0);
    }

    @Override
    public @Nullable List<StateModel> retrieve(String[] columns, String selection,
                                               String[] selectionArgs, String groupBy,
                                               String having, String orderBy, String limit) {
        if (db == null) {
            return null;
        }

        List<StateModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy,
                having, orderBy, limit)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(getColumnIndex(cursor, COLUMN_ID));
                    String state = cursor.getString(getColumnIndex(cursor, COLUMN_STATE_NAME));
                    String capitalCity = cursor.getString(getColumnIndex(cursor,
                            COLUMN_CAPITAL_CITY));
                    String secondCity = cursor.getString(getColumnIndex(cursor,
                            COLUMN_SECOND_CITY));
                    String thirdCity = cursor.getString(getColumnIndex(cursor, COLUMN_THIRD_CITY));
                    String statehood = cursor.getString(getColumnIndex(cursor, COLUMN_STATEHOOD));
                    String capitalSince = cursor.getString(getColumnIndex(cursor,
                            COLUMN_CAPITAL_SINCE));
                    String sizeRank = cursor.getString(getColumnIndex(cursor, COLUMN_SIZE_RANK));

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

    @Override
    public @Nullable List<StateModel> retrieveAll() {
        return retrieve(null, null, null, null, null, null, null);
    }

    @Override
    public int update(StateModel model) {
        if (db == null) {
            return -1;
        }

        Map<String, Object> values = new HashMap<>();
        values.put(COLUMN_STATE_NAME, model.getStateName());
        values.put(COLUMN_CAPITAL_CITY, model.getCapitalCity());
        values.put(COLUMN_SECOND_CITY, model.getSecondCity());
        values.put(COLUMN_THIRD_CITY, model.getThirdCity());
        values.put(COLUMN_STATEHOOD, model.getStatehood());
        values.put(COLUMN_CAPITAL_SINCE, model.getCapitalSince());
        values.put(COLUMN_SIZE_RANK, model.getSizeRank());

        return db.update(TABLE_NAME, values, "_id = ?",
                new String[]{String.valueOf(model.getId())});
    }

    @Override
    public int delete(long id) {
        if (db == null) {
            return -1;
        }
        return db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void deleteAll() {
        if (db == null) {
            return;
        }
        db.delete(TABLE_NAME, null, null);
    }
}
