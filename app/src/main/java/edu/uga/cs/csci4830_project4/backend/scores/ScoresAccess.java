package edu.uga.cs.csci4830_project4.backend.scores;

import static edu.uga.cs.csci4830_project4.backend.utils.BackendUtilMethods.getColumnIndex;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabaseHelper;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues;

/**
 * This class provides access to the scores table in the database and encapsulates the
 * CRUD (Create, Read, Update, Delete) operations for the score data.
 */
public class ScoresAccess implements IAccess<ScoreModel> {

    private final IDatabaseHelper helper;
    private IDatabase db;

    /**
     * Constructs a new {@link ScoresAccess} instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public ScoresAccess(Context context) {
        helper = ScoresDatabaseHelper.getInstance(context);
    }

    /**
     * Package-private constructor meant only for testing, DO NOT USE IN PRODUCTION.
     *
     * @param helper the database helper.
     */
    ScoresAccess(IDatabaseHelper helper) {
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

    private Map<String, Object> getValues(ScoreModel scoreModel) {
        Map<String, Object> values = new HashMap<>();

        String score = String.valueOf(scoreModel.getScore());
        values.put(ScoreTableValues.COLUMN_SCORE, score);

        return values;
    }

    @Override
    public ScoreModel store(ScoreModel model) {
        Map<String, Object> values = getValues(model);
        long id = db.insert(ScoreTableValues.TABLE_NAME, null, values);
        model.setId(id);
        return model;
    }

    @Override
    public ScoreModel getById(long id) {
        List<ScoreModel> models = retrieve(null, "id = ?", new String[]{String.valueOf(id)}, null
                , null, null, null);
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    public List<ScoreModel> retrieve(String[] columns, String selection, String[] selectionArgs,
                                     String groupBy, String having, String orderBy, String limit) {
        List<ScoreModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(QuizTableValues.TABLE_NAME, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit)) {
            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(getColumnIndex(cursor, QuizTableValues.COLUMN_ID));
                    String score = cursor.getString(getColumnIndex(cursor,
                            ScoreTableValues.COLUMN_SCORE));

                    ScoreModel model = new ScoreModel();
                    model.setId(id);
                    model.setScore(score);

                    models.add(model);
                }
            }
        }

        return models;
    }

    @Override
    public List<ScoreModel> retrieveAll() {
        return retrieve(null, null, null, null, null, null, null);
    }

    @Override
    public int update(ScoreModel model) {
        Map<String, Object> values = getValues(model);
        return db.update(QuizTableValues.TABLE_NAME, values, "id = ?",
                new String[]{String.valueOf(model.getId())});
    }

    @Override
    public int delete(long id) {
        if (db == null) {
            return -1;
        }
        return db.delete(QuizTableValues.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void deleteAll() {
        if (db == null) {
            return;
        }
        db.delete(QuizTableValues.TABLE_NAME, null, null);
    }
}
