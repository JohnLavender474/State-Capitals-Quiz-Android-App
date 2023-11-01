package edu.uga.cs.csci4830_project4.backend.quizzes;

import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_ANSWERS;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_CHOICES;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_ID;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_QUESTIONS;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_QUIZ_TYPE;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_RESPONSES;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_STATE_NAMES;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_TIME_CREATED;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_TIME_UPDATED;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabaseHelper;
import edu.uga.cs.csci4830_project4.backend.database.SQLiteDatabaseWrapper;

/**
 * This class is a singleton that provides access to the database. It is a subclass of
 * SQLiteOpenHelper, which is a helper class to manage database creation and version management.
 * This class is a singleton, so it can be accessed from anywhere in the application.
 */
final class QuizzesDatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper {

    private static final String TAG = "QuizzesDatabaseHelper";
    private static final String DB_NAME = "quizzes.db";
    private static final int DB_VERSION = 1;
    private static QuizzesDatabaseHelper instance;

    private QuizzesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Fetches the singleton instance of the database helper. T
     *
     * @param context The application context.
     * @return The singleton instance of the database helper.
     */
    static synchronized QuizzesDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance()");
            instance = new QuizzesDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s " +
                        "TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                        TABLE_NAME, COLUMN_ID, COLUMN_QUIZ_TYPE, COLUMN_QUESTIONS,
                        COLUMN_RESPONSES, COLUMN_CHOICES, COLUMN_ANSWERS, COLUMN_STATE_NAMES,
                        COLUMN_TIME_CREATED, COLUMN_TIME_UPDATED);

        Log.d(TAG, "onCreate(): sql=" + sql);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        Log.d(TAG, "onUpgrade(): sql=" + sql);

        db.execSQL(sql);
        onCreate(db);
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
