package io.riguron.stream.matcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllMatchTest {

    @Test
    public void whenAllSameThenTrue() {
        Match<Integer> match = new AllMatch<>(
                Arrays.asList(1, 1, 1).iterator(),
                x -> x == 1
        );

        assertTrue(match.evaluate());
    }

    @Test
    public void whenEmptyThenTrueSinceNoOneThatDoesNotSatisfy() {
        Match<Integer> match = new AllMatch<>(
                Collections.<Integer>emptyList().iterator(),
                x -> x == 1
        );

        assertTrue(match.evaluate());
    }

    @Test
    public void whenOneDoesNotMatch() {
        Match<Integer> match = new AllMatch<>(
                Arrays.asList(1, 2, 1).iterator(),
                x -> x == 1
        );

        assertFalse(match.evaluate());
    }

}