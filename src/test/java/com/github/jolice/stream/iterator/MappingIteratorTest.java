package com.github.jolice.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.*;
public class MappingIteratorTest {

    @Test
    public void whenMapAll() {

        Iterator<String> iterator = new MappingIterator<>(
                Arrays.asList(1, 2, 3).iterator(), integer -> "num: " + integer
        );

        assertTrue(iterator.hasNext());
        assertEquals("num: 1", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("num: 2", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("num: 3", iterator.next());
    }

    @Test
    public void whenEmpty() {
        Iterator<String> iterator = new MappingIterator<>(
               new ArrayList<Integer>().iterator(), String::valueOf
        );

        assertFalse(iterator.hasNext());
    }
}