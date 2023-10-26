package edu.uga.cs.csci4830_project4.backend.quizzes;

import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.getColumnIndex;
import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.listToString;
import static edu.uga.cs.csci4830_project4.backend.utils.UtilMethods.stringToList;

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
 * This class provides access to the quizzes table in the database and encapsulates the
 * CRUD (Create, Read, Update, Delete) operations for the quiz data.
 */
public class QuizzesAccess implements IAccess<QuizModel> {

    private final IDatabaseHelper helper;
    private IDatabase db;

    /**
     * Constructs a new {@link QuizzesAccess} instance with the provided context.
     *
     * @param context The application context used to initialize the database helper.
     */
    public QuizzesAccess(Context context) {
        helper = QuizzesDatabaseHelper.getInstance(context);
    }

    /**
     * Package-private constructor meant only for testing, DO NOT USE IN PRODUCTION.
     *
     * @param helper  the database helper.
     */
    QuizzesAccess(IDatabaseHelper helper) {
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
    public QuizModel store(QuizModel model) {
        Map<String, Object> values = new HashMap<>();

        String quizType = model.getQuizType() == null ? null : model.getQuizType().name();
        values.put(QuizTableValues.COLUMN_QUIZ_TYPE, quizType);

        String stateIds = listToString(model.getStateIds());
        values.put(QuizTableValues.COLUMN_STATE_IDS, stateIds);

        String responses = listToString(model.getResponses());
        values.put(QuizTableValues.COLUMN_RESPONSES, responses);

        String answeredCorrectly = listToString(model.getAnsweredCorrectly());
        values.put(QuizTableValues.COLUMN_ANSWERED_CORRECTLY, answeredCorrectly);

        values.put(QuizTableValues.COLUMN_FINISHED, model.isFinished() ? 1 : 0);

        values.put(QuizTableValues.COLUMN_SCORE, model.getScore());

        long id = db.insert(QuizTableValues.TABLE_NAME, null, values);
        model.setId(id);

        return model;
    }

    @Override
    public QuizModel getById(long id) {
        List<QuizModel> models = retrieve(null, "id = ?", new String[]{String.valueOf(id)}, null,
                null, null, null);
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    public List<QuizModel> retrieve(String[] columns, String selection, String[] selectionArgs,
                                    String groupBy, String having, String orderBy, String limit) {
        List<QuizModel> models = new ArrayList<>();

        try (Cursor cursor = db.query(QuizTableValues.TABLE_NAME, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit)) {
            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(getColumnIndex(cursor, QuizTableValues.COLUMN_ID));

                    String quizType = cursor.getString(getColumnIndex(cursor,
                            QuizTableValues.COLUMN_QUIZ_TYPE));

                    String stateIds = cursor.getString(getColumnIndex(cursor,
                            QuizTableValues.COLUMN_STATE_IDS));

                    String responses = cursor.getString(getColumnIndex(cursor,
                            QuizTableValues.COLUMN_RESPONSES));

                    String answeredCorrectly = cursor.getString(getColumnIndex(cursor,
                            QuizTableValues.COLUMN_ANSWERED_CORRECTLY));

                    boolean finished = cursor.getInt(getColumnIndex(cursor,
                            QuizTableValues.COLUMN_FINISHED)) == 1;

                    int score = cursor.getInt(getColumnIndex(cursor, QuizTableValues.COLUMN_SCORE));

                    QuizModel model = new QuizModel();
                    model.setId(id);
                    model.setQuizType(quizType == null ? null : QuizType.valueOf(quizType));
                    model.setStateIds(stringToList(stateIds, Long::parseLong));
                    model.setResponses(stringToList(responses, string -> string));
                    model.setAnsweredCorrectly(stringToList(answeredCorrectly,
                            Boolean::parseBoolean));
                    model.setFinished(finished);
                    model.setScore(score);

                    models.add(model);
                }
            }
        }

        return models;
    }

    @Override
    public List<QuizModel> retrieveAll() {
        return retrieve(null, null, null, null, null, null, null);
    }

    @Override
    public int update(QuizModel model) {
        Map<String, Object> values = new HashMap<>();

        String quizType = model.getQuizType() == null ? null : model.getQuizType().name();
        values.put(QuizTableValues.COLUMN_QUIZ_TYPE, quizType);

        String stateIds = listToString(model.getStateIds());
        values.put(QuizTableValues.COLUMN_STATE_IDS, stateIds);

        String responses = listToString(model.getResponses());
        values.put(QuizTableValues.COLUMN_RESPONSES, responses);

        String answeredCorrectly = listToString(model.getAnsweredCorrectly());
        values.put(QuizTableValues.COLUMN_ANSWERED_CORRECTLY, answeredCorrectly);

        values.put(QuizTableValues.COLUMN_FINISHED, model.isFinished() ? 1 : 0);

        values.put(QuizTableValues.COLUMN_SCORE, model.getScore());

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
