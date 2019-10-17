package io.riguron.stream.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayIteratorTest {

    @Test
    public void whenArrayContains() {
        Integer[] array = new Integer[]{1,2,3,4};

        ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());


        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());


        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());


        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenEmptyList() {
        Integer[] array = new Integer[]{};
        ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElement() {
        Integer[] array = new Integer[]{1};
        ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

        iterator.next();
        iterator.next();
    }

}