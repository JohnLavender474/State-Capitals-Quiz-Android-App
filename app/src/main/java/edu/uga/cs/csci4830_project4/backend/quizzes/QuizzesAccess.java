package edu.uga.cs.csci4830_project4.backend.quizzes;

import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.arrayToString;
import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.stringToArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;

/**
 * This class provides access to the quizzes table in the database and encapsulates the
 * CRUD (Create, Read, Update, Delete) operations for the quiz data.
 */
public class QuizzesAccess implements IAccess<QuizModel> {

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

    @Override
    public void open() {
        db = helper.getWritableDatabase();
    }

    @Override
    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    public QuizModel store(QuizModel model) {
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

    @Override
    public List<QuizModel> retrieveAll() {
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

    @Override
    public int update(QuizModel model) {
        ContentValues values = new ContentValues();

        String stateIds = arrayToString(model.getStateIds());
        values.put(QuizzesDatabaseHelper.COLUMN_STATE_IDS, stateIds);
        String responses = arrayToString(model.getResponses());
        values.put(QuizzesDatabaseHelper.COLUMN_RESPONSES, responses);
        values.put(QuizzesDatabaseHelper.COLUMN_FINISHED, model.isFinished() ? 1 : 0);
        values.put(QuizzesDatabaseHelper.COLUMN_SCORE, model.getScore());

        return db.update(QuizzesDatabaseHelper.TABLE_NAME, values, "id = ?",
                new String[]{String.valueOf(model.getId())});
    }

    @Override
    public int delete(long id) {
        if (db == null) {
            return -1;
        }
        return db.delete(QuizzesDatabaseHelper.TABLE_NAME, "id = ?",
                new String[]{String.valueOf(id)});
    }

    @Override
    public void deleteAll() {
        if (db == null) {
            return;
        }
        db.delete(QuizzesDatabaseHelper.TABLE_NAME, null, null);
    }

    private int getColumnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }
}
