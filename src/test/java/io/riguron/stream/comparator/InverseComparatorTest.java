package io.riguron.stream.comparator;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.ToIntFunction;

import static org.junit.Assert.*;

public class InverseComparatorTest {

    @Test
    public void compare() {

        Comparator<Integer> comparator = Comparator.comparingInt(x -> x);
        int compare = comparator.compare(1, 2);

        assertEquals(-1, compare);

        comparator = new InverseComparator<>(comparator);
        compare = comparator.compare(1, 2);

        assertEquals(1, compare);

    }

}