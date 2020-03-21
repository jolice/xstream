package io.riguron.xstream.iterator;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilteringIteratorTest {

    @Test
    public void whenContains() {
        Iterator<Integer> iterator = iterator(Arrays.asList(1, 3, 2, 4, 5), x -> x >= 3);

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());

    }

    @Test
    public void whenNothingThenThrows() {
        assertThrows(NoSuchElementException.class, () -> new FilteringIterator<>(Arrays.asList(1,2,3).iterator(), x -> x == 4).next());
    }

    @Test
    public void doesNotHaveNext() {
        assertFalse(iterator(Collections.emptyList(), x -> true).hasNext());
    }

    @Test
    public void allMatch() {

        Iterator<Integer> iterator = iterator(Arrays.asList(1, 3, 2, 4, 5), x -> x <= 10);

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().intValue());


        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next().intValue());

    }
    @Test
    public void noneMatch() {
        Iterator<Integer> iterator = iterator(Arrays.asList(1, 3, 2, 4, 5), x -> x >= 10);
        assertFalse(iterator.hasNext());
    }

    private Iterator<Integer> iterator(List<Integer> data, Predicate<Integer> filter) {
        return new FilteringIterator<>(data.iterator(), filter);
    }


}