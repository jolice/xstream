package io.riguron.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;


import static org.junit.jupiter.api.Assertions.*;

public class TakeWhileIteratorTest {

    @Test
    public void takeWhile() {
        TakeWhileIterator<Integer> takeWhileIterator =
                new TakeWhileIterator<>(
                        Arrays.asList(5, 6, 2, 3, 8, 9, 11, 4, 1).iterator(),
                        x -> x <= 8
                );

        assertTrue(takeWhileIterator.hasNext());
        assertEquals(5, takeWhileIterator.next().intValue());

        assertTrue(takeWhileIterator.hasNext());
        assertEquals(6, takeWhileIterator.next().intValue());

        assertTrue(takeWhileIterator.hasNext());
        assertEquals(2, takeWhileIterator.next().intValue());

        assertTrue(takeWhileIterator.hasNext());
        assertEquals(3, takeWhileIterator.next().intValue());

        assertTrue(takeWhileIterator.hasNext());
        assertEquals(8, takeWhileIterator.next().intValue());

        assertFalse(takeWhileIterator.hasNext());
    }

    @Test
    public void whenNothing() {
        TakeWhileIterator<Integer> takeWhileIterator = new TakeWhileIterator<>(Arrays.asList(1,2,3).iterator(), x -> x > 5);
        assertThrows(NoSuchElementException.class, takeWhileIterator::next);
    }

    @Test
    public void whenEmpty() {
        TakeWhileIterator<Integer> takeWhileIterator =
                new TakeWhileIterator<>(
                        Collections.<Integer>emptyList().iterator(),
                        x -> x <= 8
                );

        assertFalse(takeWhileIterator.hasNext());
    }

    @Test
    public void whenNoSuitableElements() {
        TakeWhileIterator<Integer> takeWhileIterator =
                new TakeWhileIterator<>(
                        Arrays.asList(11, 13, 51, 8, 22, 31).iterator(),
                        x -> x <= 8
                );

        assertFalse(takeWhileIterator.hasNext());

    }

}