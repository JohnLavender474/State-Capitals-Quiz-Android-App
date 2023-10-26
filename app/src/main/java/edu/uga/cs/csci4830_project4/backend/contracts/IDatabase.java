package edu.uga.cs.csci4830_project4.backend.contracts;

import android.database.Cursor;

import java.util.Map;

/**
 * This interface defines the contract for a database class that encapsulates common
 * database operations such as insert, query, update, and delete.
 */
public interface IDatabase {

    /**
     * Insert a row into the specified table with the given values.
     *
     * @param table          The name of the table to insert the data into.
     * @param nullColumnHack The name of the column that can be set to null when inserting a row.
     * @param values         A map containing the column names and values to be inserted.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     */
    long insert(String table, String nullColumnHack, Map<String, Object> values);

    /**
     * Query the specified table with the given parameters and retrieve a cursor to the results.
     *
     * @param table         The name of the table to query.
     * @param columns       A list of which columns to return. Passing null will return all columns.
     * @param selection     A filter declaring which rows to return.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values
     *                      from selectionArgs, in the order that they appear in the selection.
     * @param groupBy       A filter declaring how to group rows.
     * @param having        A filter declare which row groups to include in the cursor.
     * @param orderBy       The order in which the rows will appear in the cursor.
     * @param limit         Limits the number of rows returned by the query.
     * @return A Cursor object positioned before the first entry in the result, or null if the query
     * results in an empty set.
     */
    Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                 String groupBy, String having, String orderBy, String limit);

    /**
     * Update rows in the specified table with the given values, based on the provided
     * WHERE clause.
     *
     * @param table       The name of the table to update.
     * @param values      A map containing the new column values.
     * @param whereClause The optional WHERE clause to apply when updating.
     * @param whereArgs   You may include ?s in whereClause, which will be replaced by the values
     *                    from whereArgs, in the order that they appear in the whereClause.
     * @return The number of rows affected by the update.
     */
    int update(String table, Map<String, Object> values, String whereClause, String[] whereArgs);

    /**
     * Delete rows from the specified table based on the provided WHERE clause.
     *
     * @param table       The name of the table to delete from.
     * @param whereClause The WHERE clause to apply when deleting.
     * @param whereArgs   You may include ?s in whereClause, which will be replaced by the values
     *                    from whereArgs, in the order that they appear in the whereClause.
     * @return The number of rows affected by the delete.
     */
    int delete(String table, String whereClause, String[] whereArgs);
}
