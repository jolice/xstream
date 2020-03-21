package io.riguron.xstream.comparator;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ArbitraryTypeComparatorTest {

    @Test
    public void whenComparingNonComparableTypes() {

        assertThrows(IllegalArgumentException.class, () -> {
            ArbitraryTypeComparator<Function<?, ?>> comparator = new ArbitraryTypeComparator<>();
            comparator.compare(Function.identity(), Function.identity());
        });
    }

    @Test
    public void whenComparingComparableTypes() {
        ArbitraryTypeComparator<Integer> arbitraryTypeComparator = new ArbitraryTypeComparator<>();
        assertEquals(-1, arbitraryTypeComparator.compare(1, 2));

    }
}