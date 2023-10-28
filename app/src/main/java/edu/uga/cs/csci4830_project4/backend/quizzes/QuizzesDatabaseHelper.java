package edu.uga.cs.csci4830_project4.backend.quizzes;

import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_ANSWERED_CORRECTLY;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_CHOICES;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_FINISHED;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_ID;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_QUIZ_TYPE;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_RESPONSES;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_SCORE;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.COLUMN_STATE_IDS;
import static edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues.TABLE_NAME;

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
final class QuizzesDatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper {

    private static final String DB_NAME = "quizzes.db";
    private static final int DB_VERSION = 1;
    private static QuizzesDatabaseHelper instance;

    private QuizzesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Fetches the singleton instance of the database helper. Throws an exception if the database
     * helper is not initialized.
     *
     * @param context The application context, not used if this helper is already initialized.
     * @return The singleton instance of the database helper.
     */
    static synchronized QuizzesDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizzesDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s " +
                        "TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT)",
                TABLE_NAME, COLUMN_ID, COLUMN_CHOICES, COLUMN_QUIZ_TYPE, COLUMN_STATE_IDS,
                COLUMN_RESPONSES, COLUMN_ANSWERED_CORRECTLY, COLUMN_FINISHED, COLUMN_SCORE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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
