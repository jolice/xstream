package com.github.jolice.stream.comparator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtremeTest {

    @Test
    public void min() {

        Extreme<Integer> extreme = new Extreme<>(
                Arrays.asList(5, 3, 1, 4).iterator(), Integer::compareTo
        );

        Optional<Integer> result = extreme.get();
        assertTrue(result.isPresent());
        assertEquals(1, result.get().longValue());
    }

    @Test
    public void nax() {

        Extreme<Integer> extreme = new Extreme<>(
                Arrays.asList(5, 3, 1, 4).iterator(),Comparator.reverseOrder()
        );

        Optional<Integer> result = extreme.get();
        assertTrue(result.isPresent());
        assertEquals(5, result.get().longValue());
    }
}