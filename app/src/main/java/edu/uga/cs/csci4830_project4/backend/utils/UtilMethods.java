package edu.uga.cs.csci4830_project4.backend.utils;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for the database package.
 */
public class UtilMethods {

    /**
     * Converts a string array to a comma-separated string.
     *
     * @param a The string array to convert.
     * @return The comma-separated string.
     */
    public static <T> String listToString(List<T> a) {
        if (a == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < a.size(); i++) {
            sb.append(a.get(i));
            if (i < a.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Converts a string array to a comma-separated string.
     *
     * @param s The string array to convert.
     * @return The comma-separated string.
     */
    public static <T> List<T> stringToList(String s, Class<T> clazz) {
        if (s == null) {
            return null;
        }

        String[] strArray = s.replace("[", "").replace("]", "").split(",");
        List<T> list = new ArrayList<>();
        for (String str : strArray) {
            list.add(clazz.cast(str));
        }

        return list;
    }

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
