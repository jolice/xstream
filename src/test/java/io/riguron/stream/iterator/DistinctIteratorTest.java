package io.riguron.stream.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static java.util.Collections.emptyList;
import static org.junit.Assert.*;

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
        assertEquals(1, uniqueItr.next());


        assertTrue(uniqueItr.hasNext());
        assertEquals(2, uniqueItr.next());


        assertTrue(uniqueItr.hasNext());
        assertEquals(3, uniqueItr.next());
    }

    @Test
    public void whenOnlySame() {
        Iterator<Integer> uniqueItr = new DistinctIterator<>(Arrays.asList(1,1,1,1).iterator());

        assertTrue(uniqueItr.hasNext());
        assertEquals(1 , uniqueItr.next());
        assertFalse(uniqueItr.hasNext());
    }
}