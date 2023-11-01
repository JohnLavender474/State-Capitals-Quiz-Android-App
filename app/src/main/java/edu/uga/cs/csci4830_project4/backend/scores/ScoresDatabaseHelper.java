package edu.uga.cs.csci4830_project4.backend.scores;

import static edu.uga.cs.csci4830_project4.backend.scores.ScoreTableValues.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabaseHelper;
import edu.uga.cs.csci4830_project4.backend.database.SQLiteDatabaseWrapper;

/**
 * This class is a singleton that provides access to the database. It is a subclass of
 * SQLiteOpenHelper, which is a helper class to manage database creation and version management.
 * This class is a singleton, so it can be accessed from anywhere in the application.
 */
final class ScoresDatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper {

    private static final String DB_NAME = "scores.db";
    private static final int DB_VERSION = 1;
    private static ScoresDatabaseHelper instance;

    private ScoresDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Fetches the singleton instance of the database helper.
     *
     * @param context The application context.
     * @return The singleton instance of the database helper.
     */
    static synchronized ScoresDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ScoresDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,"
                + " %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_QUIZ_TYPE, COLUMN_SCORE,
                COLUMN_TIME_COMPLETED));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @Override
    public IDatabase getViewOnlyDatabase() {
        return new SQLiteDatabaseWrapper(getReadableDatabase());
    }

    @Override
    public IDatabase getModifiableDatabase() {
        return new SQLiteDatabaseWrapper(getWritableDatabase());
    }
}
