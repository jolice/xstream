package com.github.jolice.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmptyIteratorTest {

    @Test
    public void doesNotHasNext() {
        assertFalse(new EmptyIterator<>().hasNext());
    }

    @Test
    public void nextThrowsError() {
        assertThrows(NoSuchElementException.class, () -> new EmptyIterator<>().next());
    }
}
