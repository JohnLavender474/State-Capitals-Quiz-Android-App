package edu.uga.cs.csci4830_project4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is a singleton that provides access to the database. It is a subclass
 * of SQLiteOpenHelper, which is a helper class to manage database creation and
 * version management. This class is a singleton, so it can be accessed from
 * anywhere in the application. This class should be initialized from the
 * Application class's onCreate() method.
 */
public final class StateCapitalsDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Table name and column names
     */
    public static final String TABLE_NAME = "state_capitals";
    /**
     * COLUMN_ID is an auto-incrementing integer
     */
    public static final String COLUMN_ID = "_id";
    /**
     * COLUMN_STATE is a string
     */
    public static final String COLUMN_STATE = "state";
    /**
     * COLUMN_CHOICES is a string of the form "capital1,capital2,capital3" where
     * "capital1" is the correct answer. In the frontend, this string should be
     * split and shuffled into an array of strings.
     */
    public static final String COLUMN_CHOICES = "choices";

    private static final String DB_NAME = "StateCapitals.db";
    private static final int DB_VERSION = 1;
    private static StateCapitalsDatabaseHelper instance;

    private StateCapitalsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Fetches the singleton instance of the database helper. Throws an exception
     * if the database helper is not initialized.
     *
     * @param context the application context, not used if this helper is already initialized
     * @return the singleton instance of the database helper
     */
    public static synchronized StateCapitalsDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new StateCapitalsDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + COLUMN_STATE + " TEXT, " + COLUMN_CHOICES + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

