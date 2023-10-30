package edu.uga.cs.csci4830_project4.backend.utils;

import android.database.Cursor;

/**
 * This class provides utility methods for the database package.
 */
public class BackendUtilMethods {

    /**
     * Retrieves the column index for the given column name in a cursor.
     *
     * @param cursor The cursor from which to retrieve the column index.
     * @param column The name of the column for which to find the index.
     * @return The column index within the cursor.
     */
    public static int getColumnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }

}
