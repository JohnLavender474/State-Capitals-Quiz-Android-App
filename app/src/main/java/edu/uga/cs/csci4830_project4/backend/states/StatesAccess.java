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
import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.getColumnIndex;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private IDatabase db;

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
    public StateModel getById(long id) {
        List<StateModel> models = retrieve(null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    public List<StateModel> retrieve(String[] columns, String selection, String[] selectionArgs,
                                     String groupBy, String having, String orderBy, String limit) {
        if (db == null) {
            return null;
        }

        List<StateModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy,
                having, orderBy, limit)) {
            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
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
                }
            }
        }

        return models;
    }

    @Override
    public List<StateModel> retrieveAll() {
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

        return db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(model.getId())});
    }

    @Override
    public int delete(long id) {
        if (db == null) {
            return -1;
        }
        return db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void deleteAll() {
        if (db == null) {
            return;
        }
        db.delete(TABLE_NAME, null, null);
    }
}
