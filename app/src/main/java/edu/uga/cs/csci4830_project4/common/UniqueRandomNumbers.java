package edu.uga.cs.csci4830_project4.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains methods for getting unique random numbers.
 */
public class UniqueRandomNumbers {

    /**
     * Returns a list of unique random numbers between min inclusive and max exclusive. The amount
     * of numbers returned is equal to the amountToReturn parameter.
     *
     * @param amountToReturn the amount of numbers to return.
     * @param min            the minimum number to return.
     * @param max            the maximum number to return.
     * @return a list of unique random numbers between min and max.
     * @throws IllegalArgumentException if max - min < amountToReturn.
     */
    public static List<Integer> getUniqueRandomNumbers(int amountToReturn, int min, int max) {
        if (amountToReturn < 0) {
            throw new IllegalArgumentException("amountToReturn cannot be less than 0");
        }
        if (min >= max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        if (amountToReturn == 0) {
            return new ArrayList<>();
        }
        if (Math.abs(max - min + 1) < amountToReturn) {
            throw new IllegalArgumentException("Max - Min must be greater than amountToReturn");
        }

        List<Integer> list = new ArrayList<>();
        for (int i = min; i < max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list.subList(0, amountToReturn);
    }

}
