package edu.uga.cs.csci4830_project4.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static edu.uga.cs.csci4830_project4.utils.CommonUtilMethods.listToString;
import static edu.uga.cs.csci4830_project4.utils.CommonUtilMethods.stringToList;

import android.database.Cursor;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.utils.BackendUtilMethods;

public class CommonUtilMethodsTest {

    @Test
    public void testListToString() {
        List<String> inputList = new ArrayList<>();
        inputList.add("apple");
        inputList.add("banana");
        inputList.add("cherry");
        String expected = "[apple;banana;cherry]";
        String result = listToString(inputList);
        assertEquals(expected, result);
    }

    @Test
    public void testListToStringWithEmptyList() {
        List<String> inputList = new ArrayList<>(); // An empty list.
        String expected = "[]"; // The expected string for an empty list.
        String result = listToString(inputList);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToBooleanList() {
        String inputString = "[true;false;true]";
        List<Boolean> expected = new ArrayList<>();
        expected.add(true);
        expected.add(false);
        expected.add(true);
        List<Boolean> result = stringToList(inputString, Boolean::parseBoolean);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToBooleanListWithEmptyString() {
        String inputString = "[]"; // An empty string.
        List<Boolean> expected = new ArrayList<>(); // An empty list.
        List<Boolean> result = stringToList(inputString, Boolean::parseBoolean);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToList() {
        String inputString = "[10;20;30]";
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(30);
        List<Integer> result = stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToListWithEmptyString() {
        String inputString = "[]"; // An empty string.
        List<Integer> expected = new ArrayList<>(); // An empty list.
        List<Integer> result = stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testGetColumnIndex() {
        Cursor cursor = Mockito.mock(Cursor.class);
        Mockito.when(cursor.getColumnIndex("test_column")).thenReturn(3);
        int result = BackendUtilMethods.getColumnIndex(cursor, "test_column");
        assertEquals(3, result);
    }

    @Test
    public void testGetColumnIndexWithNonExistentColumn() {
        Cursor cursor = Mockito.mock(Cursor.class);
        Mockito.when(cursor.getColumnIndex("non_existent_column")).thenReturn(-1);
        int result = BackendUtilMethods.getColumnIndex(cursor, "non_existent_column");
        assertEquals(-1, result);
    }

    @Test
    public void testListToStringWithNullList() {
        assertNull(listToString(null));
    }

    @Test
    public void testStringToListWithEmptyStringAndParser() {
        String inputString = "[]"; // An empty string.
        List<Integer> expected = new ArrayList<>(); // An empty list.
        List<Integer> result = stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToListWithNullStringAndParser() {
        assertNull(stringToList(null, Integer::parseInt));
    }
}
