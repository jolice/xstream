package io.riguron.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class LimitedIteratorTest {

    @Test
    void testNextLimited() {

        LimitedIterator<Integer> iterator = new LimitedIterator<>(Arrays.asList(1, 2, 3, 4, 5).iterator(), 3);
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    void whenLim0() {
        LimitedIterator<Integer> iterator = new LimitedIterator<>(Arrays.asList(1, 2, 3, 4, 5).iterator(), 0);
        assertFalse(iterator.hasNext());
    }

    @Test
    void whenSrcItrEmpty() {
        LimitedIterator<Integer> iterator = new LimitedIterator<>(Collections.emptyIterator(), 2L);
        assertFalse(iterator.hasNext());
    }



}