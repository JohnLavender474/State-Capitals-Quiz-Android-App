package edu.uga.cs.csci4830_project4.common;

import static java.time.format.DateTimeFormatter.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommonUtilMethods {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = ofPattern(DATE_PATTERN);

    /**
     * Converts a LocalDateTime to a string.
     *
     * @param date The LocalDateTime to convert.
     * @return The string.
     */
    public static String dateToString(LocalDateTime date) {
        return date.format(formatter);
    }

    /**
     * Converts a string to a LocalDateTime.
     *
     * @param date The string to convert.
     * @return The LocalDateTime.
     */
    public static LocalDateTime stringToDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * Converts a list to a semi-colon-separated string.
     *
     * @param a   The list to convert.
     * @param <T> The type of the list.
     * @return The semi-colon-separated string.
     */
    public static <T> String listToString(List<T> a) {
        return listToString(a, Object::toString);
    }

    /**
     * Converts a list to a semi-colon-separated string.
     *
     * @param a The list to convert.
     * @return The semi-colon-separated string.
     */
    public static <T> String listToString(List<T> a, Function<T, String> function) {
        if (a == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < a.size(); i++) {
            String s = function.apply(a.get(i));
            sb.append(s);
            if (i < a.size() - 1) {
                sb.append(";");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Converts a list to a comma-separated string.
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
