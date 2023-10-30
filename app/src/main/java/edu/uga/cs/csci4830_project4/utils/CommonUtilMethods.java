package edu.uga.cs.csci4830_project4.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommonUtilMethods {

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
                sb.append(";");
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
    public static <T> List<T> stringToList(String s, Function<String, T> function) {
        if (s == null) {
            return null;
        }
        if (s.isBlank() || s.equals("[]")) {
            return new ArrayList<>();
        }

        String[] strArray = s.replace("[", "").replace("]", "").split(";");
        List<T> list = new ArrayList<>();
        for (String str : strArray) {
            list.add(function.apply(str));
        }

        return list;
    }

}
