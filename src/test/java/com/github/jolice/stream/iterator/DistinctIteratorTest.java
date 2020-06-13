package com.github.jolice.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.*;

public class DistinctIteratorTest {

    @Test
    public void whenEmptyList() {
        Iterator<Integer> distinctIterator = new DistinctIterator<>(new ArrayList<Integer>().iterator());
        assertFalse(distinctIterator.hasNext());

    }

    @Test
    public void whenOnlyUnique() {
        Iterator<Integer> uniqueItr = new DistinctIterator<>(Arrays.asList(1,2,3).iterator());

        assertTrue(uniqueItr.hasNext());
        assertEquals(1, uniqueItr.next().intValue());


        assertTrue(uniqueItr.hasNext());
        assertEquals(2, uniqueItr.next().intValue());


        assertTrue(uniqueItr.hasNext());
        assertEquals(3, uniqueItr.next().intValue());
    }

    @Test
    public void whenOnlySame() {
        Iterator<Integer> uniqueItr = new DistinctIterator<>(Arrays.asList(1,1,1,1).iterator());

        assertTrue(uniqueItr.hasNext());
        assertEquals(1 , uniqueItr.next().intValue());
        assertFalse(uniqueItr.hasNext());
    }
}