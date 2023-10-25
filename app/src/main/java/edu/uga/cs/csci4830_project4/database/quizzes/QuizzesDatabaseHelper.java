package edu.uga.cs.csci4830_project4.database.quizzes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is a singleton that provides access to the database. It is a subclass of
 * SQLiteOpenHelper, which is a helper class to manage database creation and version management.
 * This class is a singleton, so it can be accessed from anywhere in the application. This class
 * should be initialized from the Application class's onCreate() method.
 */
public final class QuizzesDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Table name and column names
     */
    public static final String TABLE_NAME = "incomplete_quiz";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STATE_IDS = "state_ids";
    public static final String COLUMN_RESPONSES = "responses";
    public static final String COLUMN_FINISHED = "finished";
    public static final String COLUMN_SCORE = "score";

    private static final String DB_NAME = "IncompleteQuizzes.db";
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
    public static synchronized QuizzesDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizzesDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + COLUMN_STATE_IDS + " TEXT, " + COLUMN_RESPONSES + " TEXT, " +
                COLUMN_FINISHED + " INTEGER, " + COLUMN_SCORE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
