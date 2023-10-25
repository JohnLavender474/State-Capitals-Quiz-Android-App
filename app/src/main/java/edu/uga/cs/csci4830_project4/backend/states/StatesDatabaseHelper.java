package edu.uga.cs.csci4830_project4.backend.states;

import static edu.uga.cs.csci4830_project4.backend.utils.ConstVals.STATES_CSV;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is a singleton that provides access to the database. It is a subclass
 * of SQLiteOpenHelper, which is a helper class to manage database creation and
 * version management. This class is a singleton, so it can be accessed from
 * anywhere in the application. This class should be initialized from the
 * Application class's onCreate() method.
 */
final class StatesDatabaseHelper extends SQLiteOpenHelper {

    static final String TABLE_NAME = "states";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_STATE_NAME = "state_name";
    static final String COLUMN_CAPITAL_CITY = "capital_city";
    static final String COLUMN_SECOND_CITY = "second_city";
    static final String COLUMN_THIRD_CITY = "third_city";
    static final String COLUMN_STATEHOOD = "statehood";
    static final String COLUMN_CAPITAL_SINCE = "capital_since";
    static final String COLUMN_SIZE_RANK = "size_rank";

    private static final String DB_NAME = "states.db";
    private static final int DB_VERSION = 1;

    @SuppressLint("StaticFieldLeak")
    private static StatesDatabaseHelper instance;

    private final Context context;

    private StatesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    /**
     * Fetches the singleton instance of the database helper. Throws an exception
     * if the database helper is not initialized.
     *
     * @param context the application context, not used if this helper is already initialized
     * @return the singleton instance of the database helper
     */
    static synchronized StatesDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new StatesDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + COLUMN_STATE_NAME + " TEXT, " + COLUMN_CAPITAL_CITY + " TEXT," +
                " " +
                COLUMN_SECOND_CITY + " TEXT, " + COLUMN_THIRD_CITY + " TEXT, " + COLUMN_STATEHOOD +
                " TEXT, " + COLUMN_CAPITAL_SINCE + " TEXT, " + COLUMN_SIZE_RANK + " INTEGER)");

        try {
            final InputStream data = context.getAssets().open(STATES_CSV);
            try (data) {
                CSVReader reader = new CSVReader(new InputStreamReader(data));
                // skip the first row that contains column titles
                boolean pastFirstRow = false;
                String[] nextRow;
                while ((nextRow = reader.readNext()) != null) {
                    if (!pastFirstRow) {
                        pastFirstRow = true;
                        continue;
                    }

                    ContentValues values = new ContentValues();

                    values.put(COLUMN_STATE_NAME, nextRow[0]);
                    values.put(COLUMN_CAPITAL_CITY, nextRow[1]);
                    values.put(COLUMN_SECOND_CITY, nextRow[2]);
                    values.put(COLUMN_THIRD_CITY, nextRow[3]);
                    values.put(COLUMN_STATEHOOD, nextRow[4]);
                    values.put(COLUMN_CAPITAL_SINCE, nextRow[5]);
                    values.put(COLUMN_SIZE_RANK, nextRow[6]);

                    if (db.insert(TABLE_NAME, null, values) == -1) {
                        throw new RuntimeException("Error while inserting into table [" +
                                TABLE_NAME + "]: " + values);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

