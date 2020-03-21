package io.riguron.xstream.matcher;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class AnyMatchTest {

    @Test
    public void anyMatchTest() {
        Match<Integer> match = new AnyMatch<>(
                Arrays.asList(1, 2, 3).iterator(),
                x -> x % 2 == 0
        );
        assertTrue(
                match.evaluate());
    }

    @Test
    public void whenNoMatching() {
        Match<Integer> match = new AnyMatch<>(
                Arrays.asList(1, 3, 5).iterator(),
                x -> x % 2 == 0
        );
        assertFalse(
                match.evaluate());
    }

    @Test
    public void whenEmptyThenFalse() {
        Match<Integer> match = new AnyMatch<>(
                Collections.<Integer>emptyList().iterator(),
                x -> true
        );
        assertFalse(
                match.evaluate());
    }
}