package io.riguron.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DropWhileIteratorTest {

    @Test
    public void doDropWhile() {
        DropWhileIterator<Integer> dropWhile = new DropWhileIterator<>(
                Arrays.asList(2,3,4,5,8,1,9,20).iterator(),
                x -> x < 8
        );

        assertTrue(dropWhile.hasNext());
        assertEquals(8, dropWhile.next().intValue());

        assertTrue(dropWhile.hasNext());
        assertEquals(1, dropWhile.next().intValue());

        assertTrue(dropWhile.hasNext());
        assertEquals(9, dropWhile.next().intValue());

        assertTrue(dropWhile.hasNext());
        assertEquals(20, dropWhile.next().intValue());

        assertFalse(dropWhile.hasNext());
    }


    @Test
    public void whenNothingSuitsThenThrowsError() {
        assertThrows(NoSuchElementException.class, () -> new DropWhileIterator<>(Arrays.asList(1,2,3).iterator(), x -> x < 5).next());
    }

    @Test
    public void whenEmpty() {
        DropWhileIterator<Integer> dropWhile = new DropWhileIterator<>(
                Collections.emptyIterator(),
                x -> x < 8
        );
        assertFalse(dropWhile.hasNext());
    }

    @Test
    public void whenNoneMatch() {
        DropWhileIterator<Integer> dropWhile = new DropWhileIterator<>(
                Arrays.asList(2, 3, 5).iterator(),
                x -> x < 8
        );

      assertFalse(dropWhile.hasNext());
    }

    @Test
    public void whenAllMatch() {
        DropWhileIterator<Integer> dropWhile = new DropWhileIterator<>(
                Arrays.asList(5, 9, 8).iterator(),
                x -> x < 2
        );

        assertTrue(dropWhile.hasNext());
        assertEquals(5, dropWhile.next().intValue());


        assertTrue(dropWhile.hasNext());
        assertEquals(9, dropWhile.next().intValue());


        assertTrue(dropWhile.hasNext());
        assertEquals(8, dropWhile.next().intValue());

        assertFalse(dropWhile.hasNext());

    }
}