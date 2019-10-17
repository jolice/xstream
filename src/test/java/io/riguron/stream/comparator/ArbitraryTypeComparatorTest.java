package io.riguron.stream.comparator;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class ArbitraryTypeComparatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenComparingNonComparableTypes() {

        ArbitraryTypeComparator<Function<?, ?>> comparator = new ArbitraryTypeComparator<>();

        comparator.compare(Function.identity(), Function.identity());
    }

    @Test
    public void whenComparingComparableTypes() {
        ArbitraryTypeComparator<Integer> arbitraryTypeComparator = new ArbitraryTypeComparator<>();
        assertEquals(-1, arbitraryTypeComparator.compare(1, 2));

    }
}