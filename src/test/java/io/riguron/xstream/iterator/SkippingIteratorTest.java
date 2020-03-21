package io.riguron.xstream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.*;

public class SkippingIteratorTest {

    @Test
    public void whenSkippedValidAmount() {

        Iterator<Integer> iterator = iterator(2);

        for (int i = 3; i <= 7; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }


    }

    @Test
    public void whenSkippedTooMuch() {

        Iterator<Integer> iterator = iterator(100);

        assertFalse(iterator.hasNext());

    }

    @Test
    public void whenSkippedEverything() {
        Iterator<Integer> iterator = iterator(7);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenSkippedNegative() {
        Iterator<Integer> iterator = iterator(-1);
        fullExists(iterator);
    }

    @Test
    public void whenSkippedZero() {
        Iterator<Integer> iterator = iterator(0);
        fullExists(iterator);

    }

    private void fullExists(Iterator<Integer> iterator) {
        for (int i = 1; i <= 7; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }
    }

    private Iterator<Integer> iterator(int x) {
        return new SkippingIterator<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7).iterator(), x);
    }


}