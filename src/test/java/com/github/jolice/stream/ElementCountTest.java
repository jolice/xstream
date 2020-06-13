package com.github.jolice.stream;

import com.github.jolice.stream.operation.ElementCount;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElementCountTest {



    @Test
    public void whenListContainsElements() {



        ElementCount elementCount = new ElementCount(
                Arrays.asList(1, 2, 3, 4).iterator()
        );

        assertEquals(4, elementCount.evaluate());
    }

    @Test
    public void whenEmpty() {
        ElementCount elementCount = new ElementCount(
                Collections.<Integer>emptyList().iterator()
        );

        assertEquals(0, elementCount.evaluate());
    }
}