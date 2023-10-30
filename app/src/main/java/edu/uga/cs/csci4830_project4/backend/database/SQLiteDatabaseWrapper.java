package edu.uga.cs.csci4830_project4.backend.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;

/**
 * This class provides a wrapper for the SQLiteDatabase class. The purpose of this class is to
 * provide a layer of abstraction between the database and the rest of the application which is
 * useful for testing purposes along with possible extensibility and maintainability benefits.
 * This class implements the IDatabase interface, which provides a contract for database classes.
 */
public class SQLiteDatabaseWrapper implements IDatabase {

    private final SQLiteDatabase database;

    public SQLiteDatabaseWrapper(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Converts a map of key-value pairs to ContentValues, suitable for database operations.
     *
     * @param map The map containing key-value pairs to be converted.
     * @return ContentValues containing the data from the input map.
     */
    public static ContentValues mapToContentValues(Map<String, Object> map) {
        ContentValues values = new ContentValues();
        map.forEach((key, value) -> {
            if (value == null) {
                values.putNull(key);
            } else if (value instanceof Byte b) {
                values.put(key, b);
            } else if (value instanceof Short s) {
                values.put(key, s);
            } else if (value instanceof Integer i) {
                values.put(key, i);
            } else if (value instanceof Long l) {
                values.put(key, l);
            } else if (value instanceof Float f) {
                values.put(key, f);
            } else if (value instanceof Double d) {
                values.put(key, d);
            } else if (value instanceof Boolean b) {
                values.put(key, b);
            } else if (value instanceof String s) {
                values.put(key, s);
            } else if (value instanceof byte[] b) {
                values.put(key, b);
            }
        });
        return values;
    }


    @Override
    public long insert(String table, String nullColumnHack, Map<String, Object> values) {
        return database.insert(table, null, mapToContentValues(values));
    }

    @Override
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {
        return database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy,
                limit);
    }

    @Override
    public int update(String table, Map<String, Object> values, String whereClause,
                      String[] whereArgs) {
        return database.update(table, mapToContentValues(values), whereClause, whereArgs);
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return database.delete(table, whereClause, whereArgs);
    }
}
