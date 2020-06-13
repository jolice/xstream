package com.github.jolice.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReverseIteratorTest {

    @Test
    void reverses() {

        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
        Iterator<Integer> iterator = new ReverseIterator<>(values.iterator());

        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());

        assertFalse(iterator.hasNext());


    }
}
