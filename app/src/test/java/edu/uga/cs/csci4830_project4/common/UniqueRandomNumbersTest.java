package edu.uga.cs.csci4830_project4.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueRandomNumbersTest {

    @Test
    public void testGenerateUniqueRandomNumbers() {
        int[][] tests = new int[][]{
                {5, 0, 50},
                {3, -5, 5},
                {2, 40, 43},
                {10, 0, 50},
                {6, 0, 50},
                {20, 0, 100},
                {2, 0, 3},
                {1, 0, 1},
                {0, 0, 1}
        };

        for (int i = 0; i < tests.length; i++) {
            int[] test = tests[i];
            System.out.println("Test " + i + ": " + Arrays.toString(test));

            List<Integer> result = UniqueRandomNumbers.getUniqueRandomNumbers(test[0], test[1], test[2]);
            // Check the number of generated elements
            assertEquals(test[0], result.size());

            // Check if all generated numbers are within the specified range
            for (int num : result) {
                assertTrue(num >= test[1] && num < test[2]);
            }

            // Check if all generated numbers are unique
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < result.size(); j++) {
                int value = result.get(j);
                assertFalse(set.contains(value));
                set.add(value);
            }

            System.out.println("Result " + i + ": " + result);
        }
    }

    @Test
    public void testInvalidRange() {
        // Test case 3: Attempt to generate more unique random numbers than the range allows
        assertThrows(IllegalArgumentException.class,
                () -> UniqueRandomNumbers.getUniqueRandomNumbers(10, 1, 5));

        // Test case 4: Attempt to generate random numbers with an invalid range
        assertThrows(IllegalArgumentException.class,
                () -> UniqueRandomNumbers.getUniqueRandomNumbers(3, 10, 5));
    }
}

