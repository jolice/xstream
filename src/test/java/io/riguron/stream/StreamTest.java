package io.riguron.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StreamTest {

    @Test
    public void runFullStreamOps() {

        Target target = mock(Target.class);

        List<Integer> collect = StreamFactory.of(6, 8, 3, 8, 5, 2, 7, 4, 1)
                .skip(2)
                .peek(target::peekMe)
                .limit(5)
                .distinct()
                .sorted()
                .map(x -> x + 2)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(4, 5, 7, 9, 10), collect);
        Arrays.asList(3, 8, 5, 2, 7)
                .forEach(x -> verify(target).peekMe(x));
    }

    @Test
    public void whenFilter() {
        assertEquals(Arrays.asList(2, 4),

                StreamFactory.of(1, 2, 3, 4).filter(x -> x % 2 == 0)
                        .collect(toList())
        );
    }

    @Test
    public void whenMap() {
        assertEquals(Arrays.asList(1D, 4D, 9D, 16D),

                StreamFactory.of(1, 2, 3, 4).map(x -> Math.pow(x, 2))
                        .collect(toList())
        );
    }

    @Test
    public void whenFlatMap() {

        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9),
                StreamFactory.of(Arrays.asList(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9)
                )).flatMap(StreamFactory::of)
                        .collect(toList())
        );
    }

    @Test
    public void whenDistinct() {
        assertEquals(Arrays.asList(4, 5, 6, 7),
                StreamFactory.of(4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7).distinct().collect(toList()));
    }

    @Test
    public void whenSorted() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                StreamFactory.of(3, 5, 2, 4, 1).sorted().collect(toList())
        );
    }

    @Test
    public void whenSortedReverseOrder() {
        assertEquals(
                Arrays.asList(5, 4, 3, 2, 1),
                StreamFactory.of(3, 5, 2, 4, 1).sorted(reverseOrder()).collect(toList())
        );
    }

    @Test
    public void peek() {
        Target target = mock(Target.class);

        StreamFactory.of(1, 2, 3, 4, 5)
                .peek(target::peekMe)
                .count(); // calling terminal op for lazy evaluation

        for (int i = 1; i <= 5; i++) {
            verify(target).peekMe(eq(i));
        }
    }

    @Test
    public void limit() {
        assertEquals(Arrays.asList(1, 2),
                StreamFactory.of(1, 2, 3, 4, 5).limit(2).collect(toList()));
    }

    @Test
    public void skip() {
        assertEquals(Arrays.asList(3, 4, 5),
                StreamFactory.of(1, 2, 3, 4, 5).skip(2).collect(toList()));
    }

    @Test
    public void forEach() {

        Target target = mock(Target.class);
        StreamFactory.of(1, 2, 3, 4).forEach(target::peekMe);

        for (int i = 1; i <= 4; i++) {
            verify(target).peekMe(eq(i));
        }
    }

    @Test
    public void toArray() {
        Integer[] expected = {1, 2, 3};

        assertTrue(
                Arrays.equals(expected,
                        StreamFactory.of(1, 2, 3).toArray())
        );
    }

    @Test
    public void toGenericArray() {
        Integer[] integers = StreamFactory.of(1, 2, 3).toArray(Integer[]::new);
        assertTrue(Arrays.equals(new Integer[]{1,2,3}, integers));
    }

    @Test
    public void reduceWithIdentity() {
        assertEquals(
                10,
                StreamFactory.of(1, 2, 3, 4).reduce(0, Integer::sum)
        );
    }


    @Test
    public void reduceWithIdentityAndAccumulator() {
        assertEquals(
                10,
                StreamFactory.of(1, 2, 3, 4).reduce(0,
                        (a, b) -> a + b, Integer::sum)
        );
    }

    @Test
    public void reduceWithSupplier() {
        Optional<Integer> result = StreamFactory.of(1, 2, 3, 4).reduce(Integer::sum);
        assertTrue(result.isPresent());
        assertEquals(
                10,
                result.get()
        );
    }

    @Test
    public void collectSupplierAndAccumulator() {
        assertEquals("abc",
                StreamFactory.of(Arrays.asList('a', 'b', 'c'))
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString());
    }

    @Test
    public void collectToSet() {
        assertEquals(Arrays.asList(1, 3, 2, 4), StreamFactory.of(1, 3, 2, 4).collect(toList()));
    }

    @Test
    public void min() {
        Optional<Integer> result = StreamFactory.of(1, 3, 2, 4).min(Integer::compareTo);
        assertTrue(result.isPresent());
        assertEquals(1, result.get());

        assertFalse(
                StreamFactory.of(1, 2, 3).filter(x -> x > 5).min(Integer::compareTo)
                        .isPresent());
    }

    @Test
    public void max() {
        Optional<Integer> result = StreamFactory.of(1, 4, 3, 2).max(Integer::compareTo);
        assertTrue(result.isPresent());
        assertEquals(4, result.get());

        assertFalse(
                StreamFactory.of(1, 2, 3).filter(x -> x > 5).max(Integer::compareTo)
                        .isPresent());
    }


    @Test
    public void count() {
        assertEquals(3L,
                StreamFactory.of(1, 2, 3).count());
        assertEquals(0L, StreamFactory.of(emptyList()).count());
    }

    @Test
    public void anyMatch() {
        assertFalse(
                StreamFactory.of(1, 2, 3).anyMatch(x -> x == 5));

        assertTrue(
                StreamFactory.of(1, 5, 3).anyMatch(x -> x == 5));
    }

    @Test
    public void allMatch() {
        assertTrue(StreamFactory.empty().allMatch(x -> false));
        assertTrue(StreamFactory.of(1, 1, 1, 1).allMatch(x -> true));
        assertFalse(StreamFactory.of(1, 1, 2, 3).allMatch(x -> x == 1));
    }

    @Test
    public void noneMatch() {
        assertFalse(StreamFactory.of(1, 2, 3).noneMatch(x -> x == 1));
        assertTrue(StreamFactory.of(4, 2, 3).noneMatch(x -> x == 1));
        assertTrue(StreamFactory.empty().noneMatch(x -> true));
    }

    @Test
    public void findFirst() {
        Optional<Integer> result = StreamFactory.of(1, 2, 3).filter(x -> x.equals(2))
                .findFirst();

        assertTrue(result.isPresent());
        assertEquals(2, result.get());

    }


    interface Target {

        void peekMe(Integer x);
    }
}
