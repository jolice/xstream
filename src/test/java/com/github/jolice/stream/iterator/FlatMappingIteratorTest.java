package com.github.jolice.stream.iterator;

import com.github.jolice.stream.Stream;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Collections.emptyList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlatMappingIteratorTest {

    @Test
    public void whenListWithNoEmptyStreams() {

        testIterator(
                Arrays.asList(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9)
                ), 1, 2, 3, 4, 5, 6, 7, 8, 9
        );
    }

    @Test
    public void whenEmptyThenThrowsError() {
        assertThrows(NoSuchElementException.class, () ->
                iterator(Arrays.asList(emptyList(), emptyList())).next());
    }

    @Test
    public void whenFirstSkipped() {
        testIterator(
                Arrays.asList(
                        emptyList(),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9)
                ), 4, 5, 6, 7, 8, 9
        );
    }

    @Test
    public void whenMidSkipped() {
        testIterator(
                Arrays.asList(
                        Arrays.asList(4, 5, 6),
                        emptyList(),
                        Arrays.asList(7, 8, 9)
                ), 4, 5, 6, 7, 8, 9
        );
    }

    @Test
    public void whenEndSkipped() {
        testIterator(
                Arrays.asList(
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9),
                        emptyList()
                ), 4, 5, 6, 7, 8, 9
        );
    }

    @Test
    public void whenFirstSkippedTwice() {
        testIterator(
                Arrays.asList(
                        emptyList(),
                        emptyList(),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9)
                ), 4, 5, 6, 7, 8, 9
        );
    }



    @Test
    public void whenEmpty() {
        testIterator(emptyList());
    }


    @Test
    public void whenEndSkippedTwice() {
        testIterator(
                Arrays.asList(
                        Arrays.asList(6, 2, 4),
                        Arrays.asList(9, 8, 3),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9),
                        emptyList(),
                        emptyList()
                ), 6, 2, 4, 9, 8, 3, 4, 5, 6, 7, 8, 9
        );
    }

    @Test
    public void whenMidSkippedTwice() {
        testIterator(
                Arrays.asList(
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(4, 2, 2),
                        emptyList(),
                        emptyList(),
                        Arrays.asList(7, 8, 9)
                ), 4, 5, 6, 4, 2, 2, 7, 8, 9
        );
    }

    @Test
    public void whenOnlyMid() {
        testIterator(
                Arrays.asList(
                        emptyList(),
                        emptyList(),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9),
                        emptyList(),
                        emptyList()
                ), 4, 5, 6, 7, 8, 9
        );
    }


    private void testIterator(List<List<Integer>> lists, Integer... expectedElements) {
        Iterator<Integer> iterator = iterator(lists);
        int index = 0;
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            assertNotNull(next);
            assertEquals(next, expectedElements[index++]);
        }
    }

    private Iterator<Integer> iterator(List<List<Integer>> lists) {
        return new FlatMappingIterator<>(this::makeStream, lists.iterator());
    }

    private Stream<? extends Integer> makeStream(List<Integer> ints) {
        Stream<Integer> stream = mock(Stream.class);
        when(stream.iterator()).thenReturn(ints.iterator());
        return stream;
    }

}