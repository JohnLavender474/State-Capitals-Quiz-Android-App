package edu.uga.cs.csci4830_project4.backend.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.database.Cursor;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UtilMethodsTest {

    @Test
    public void testListToString() {
        List<String> inputList = new ArrayList<>();
        inputList.add("apple");
        inputList.add("banana");
        inputList.add("cherry");
        String expected = "[apple,banana,cherry]";
        String result = UtilMethods.listToString(inputList);
        assertEquals(expected, result);
    }

    @Test
    public void testListToStringWithEmptyList() {
        List<String> inputList = new ArrayList<>(); // An empty list.
        String expected = "[]"; // The expected string for an empty list.
        String result = UtilMethods.listToString(inputList);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToBooleanList() {
        String inputString = "[true,false,true]";
        List<Boolean> expected = new ArrayList<>();
        expected.add(true);
        expected.add(false);
        expected.add(true);
        List<Boolean> result = UtilMethods.stringToList(inputString, Boolean::parseBoolean);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToBooleanListWithEmptyString() {
        String inputString = "[]"; // An empty string.
        List<Boolean> expected = new ArrayList<>(); // An empty list.
        List<Boolean> result = UtilMethods.stringToList(inputString, Boolean::parseBoolean);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToList() {
        String inputString = "[10,20,30]";
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(30);
        List<Integer> result = UtilMethods.stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToListWithEmptyString() {
        String inputString = "[]"; // An empty string.
        List<Integer> expected = new ArrayList<>(); // An empty list.
        List<Integer> result = UtilMethods.stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testGetColumnIndex() {
        Cursor cursor = Mockito.mock(Cursor.class);
        Mockito.when(cursor.getColumnIndex("test_column")).thenReturn(3);
        int result = UtilMethods.getColumnIndex(cursor, "test_column");
        assertEquals(3, result);
    }

    @Test
    public void testGetColumnIndexWithNonExistentColumn() {
        Cursor cursor = Mockito.mock(Cursor.class);
        Mockito.when(cursor.getColumnIndex("non_existent_column")).thenReturn(-1);
        int result = UtilMethods.getColumnIndex(cursor, "non_existent_column");
        assertEquals(-1, result);
    }

    @Test
    public void testListToStringWithNullList() {
        assertNull(UtilMethods.listToString(null));
    }

    @Test
    public void testStringToListWithEmptyStringAndParser() {
        String inputString = "[]"; // An empty string.
        List<Integer> expected = new ArrayList<>(); // An empty list.
        List<Integer> result = UtilMethods.stringToList(inputString, Integer::parseInt);
        assertEquals(expected, result);
    }

    @Test
    public void testStringToListWithNullStringAndParser() {
        assertNull(UtilMethods.stringToList(null, Integer::parseInt));
    }
}
