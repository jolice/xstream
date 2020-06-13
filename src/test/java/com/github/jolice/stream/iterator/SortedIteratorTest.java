package com.github.jolice.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
public class SortedIteratorTest {

    @Test
    public void sortedIterator() {
        Iterator<Integer> iterator = new SortedIterator<>(
                Arrays.asList(8, 3, 2, 4, 5).iterator(), Comparator.comparingInt(o -> o)
        );

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(8, iterator.next().intValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenEmpty() {
        Iterator<Integer> iterator = new SortedIterator<>(
                Collections.<Integer>emptyList().iterator(), Comparator.comparingInt(o -> o)
        );
        assertFalse(iterator.hasNext());
    }

}