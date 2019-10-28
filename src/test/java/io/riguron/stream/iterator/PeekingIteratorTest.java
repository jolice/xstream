package io.riguron.stream.iterator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PeekingIteratorTest {

    @Test
    public void reallyPicked() {
        Verify target = mock(Verify.class);

        Iterator<Integer> peekingItr = new PeekingIterator<>(Arrays.asList(1, 2, 3).iterator(), target::callMe);

        assertTrue(peekingItr.hasNext());
        assertEquals(1, peekingItr.next().intValue());
        verify(target).callMe(eq(1));


        assertTrue(peekingItr.hasNext());
        assertEquals(2, peekingItr.next().intValue());
        verify(target).callMe(eq(2));


        assertTrue(peekingItr.hasNext());
        assertEquals(3, peekingItr.next().intValue());
        verify(target).callMe(eq(3));
    }

    @Test
    public void whenEmpty() {
        Iterator<Integer> peekingItr = new PeekingIterator<>(new ArrayList<Integer>().iterator(), x -> {
        });

        assertFalse(peekingItr.hasNext());
    }


    interface Verify {

        void callMe(Integer integer);
    }
}