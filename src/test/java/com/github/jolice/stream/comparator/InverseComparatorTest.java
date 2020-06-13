package com.github.jolice.stream.comparator;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InverseComparatorTest {

    @Test
    public void compare() {

        Comparator<Integer> comparator = Comparator.comparingInt(x -> x);
        int compare = comparator.compare(1, 2);

        assertEquals(-1, compare);

        comparator = new InverseComparator<>(comparator);
        compare = comparator.compare(1, 2);

        assertEquals(1, compare);

    }

}