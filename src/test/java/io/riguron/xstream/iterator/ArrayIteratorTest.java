package io.riguron.xstream.iterator;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIteratorTest {

    @Test
    public void whenArrayContains() {
        Integer[] array = new Integer[]{1, 2, 3, 4};

        ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().intValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenEmptyList() {
        Integer[] array = new Integer[]{};
        ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenNoElement() {

        assertThrows(NoSuchElementException.class, () -> {
            Integer[] array = new Integer[]{1};
            ArrayIterator<Integer> iterator = new ArrayIterator<>(array);

            iterator.next().intValue();
            iterator.next().intValue();
        });


    }

}