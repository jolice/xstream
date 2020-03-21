package io.riguron.xstream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class InterspersingIteratorTest {

    @Test
    void intersperses() {
        Iterator<Integer> iterator = new InterspersingIterator<>(
                Arrays.asList(1, 2, 3).iterator(),
                5
        );

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());
        assertFalse(iterator.hasNext());
    }
}
