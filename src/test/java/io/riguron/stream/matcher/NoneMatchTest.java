package io.riguron.stream.matcher;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;

public class NoneMatchTest {


    @Test
    public void whenNoneMatch() {
        NoneMatch<Integer> noneMatch = new NoneMatch<>(
                Arrays.asList(1, 2, 3).iterator(),
                x -> x == 5
        );

        assertTrue(
                noneMatch.evaluate());
    }

    @Test
    public void whenOneMatches() {

        NoneMatch<Integer> noneMatch = new NoneMatch<>(
                Arrays.asList(1, 5, 3).iterator(),
                x -> x == 5
        );

        assertFalse(
                noneMatch.evaluate());
    }

    @Test
    public void whenEmptyThenTrue() {

        NoneMatch<Integer> noneMatch = new NoneMatch<>(
                Collections.<Integer>emptyList().iterator(),
                x -> x == 5
        );

        assertTrue(
                noneMatch.evaluate());
    }
}