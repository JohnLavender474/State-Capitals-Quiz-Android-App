package edu.uga.cs.csci4830_project4.database.quizzes;

import static edu.uga.cs.csci4830_project4.database.utils.UtilMethods.arrayToString;
import static edu.uga.cs.csci4830_project4.database.utils.UtilMethods.stringToArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides access to the quiz_in_progress table in the database and encapsulates the
 * CRUD
 * (Create, Read, Update, Delete) operations for the quiz in progress data.
 */
public class QuizzesAccess {

    private final SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    /**
     * Constructs a new {@link QuizzesAccess} instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public QuizzesAccess(Context context) {
        helper = QuizzesDatabaseHelper.getInstance(context);
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
     * Stores a quiz in progress record in the database and returns the model with its assigned ID.
     *
     * @param model The {@link QuizModel} representing the quiz in progress to store.
     * @return The stored {@link QuizModel} with its assigned ID.
     */
    public QuizModel storeQuiz(QuizModel model) {
        ContentValues values = new ContentValues();
        String stateIds = arrayToString(model.getStateIds());
        values.put(QuizzesDatabaseHelper.COLUMN_STATE_IDS, stateIds);
        String responses = arrayToString(model.getResponses());
        values.put(QuizzesDatabaseHelper.COLUMN_RESPONSES, responses);
        values.put(QuizzesDatabaseHelper.COLUMN_FINISHED, model.isFinished() ? 1 : 0);
        values.put(QuizzesDatabaseHelper.COLUMN_SCORE, model.getScore());

        long id = db.insert(QuizzesDatabaseHelper.TABLE_NAME, null, values);
        model.setId(id);

        return model;
    }

    /**
     * Retrieves from the database a list of all {@link QuizModel} objects representing
     * the quizzes in progress.
     *
     * @return A list of {@link QuizModel} objects representing entries in the database.
     */
    public List<QuizModel> retrieveAllQuizzes() {
        List<QuizModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(QuizzesDatabaseHelper.TABLE_NAME, null, null, null, null,
                null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(getColumnIndex(cursor,
                            QuizzesDatabaseHelper.COLUMN_ID));
                    String stateIds = cursor.getString(getColumnIndex(cursor,
                            QuizzesDatabaseHelper.COLUMN_STATE_IDS));
                    String responses = cursor.getString(getColumnIndex(cursor,
                            QuizzesDatabaseHelper.COLUMN_RESPONSES));
                    boolean finished = cursor.getInt(getColumnIndex(cursor,
                            QuizzesDatabaseHelper.COLUMN_FINISHED)) == 1;
                    String score = cursor.getString(getColumnIndex(cursor,
                            QuizzesDatabaseHelper.COLUMN_SCORE));

                    QuizModel model = new QuizModel();
                    model.setId(id);
                    model.setStateIds(stringToArray(stateIds));
                    model.setResponses(stringToArray(responses));
                    model.setFinished(finished);
                    model.setScore(score);

                    models.add(model);
                }
            }
        }

        return models;
    }

    private int getColumnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }
}
