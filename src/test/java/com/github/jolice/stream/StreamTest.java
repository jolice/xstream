package com.github.jolice.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StreamTest {

    @Test
    void runFullStreamOps() {

        Target target = mock(Target.class);

        List<Integer> collect = StreamFactory.stream(6, 8, 3, 8, 5, 2, 7, 4, 1)
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
    void iterate() {
        List<Integer> collect = StreamFactory.iterate(1, integer -> integer + 1).limit(5).collect(toList());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), collect);
    }

    @Test
    void emptyStream() {
        assertEquals(StreamFactory.stream(), new EmptyStream<>());
    }

    @Test
    void customOp() {
        assertEquals(Arrays.asList(2, 3, 4, 5),
                StreamFactory.stream(1, 2, 3, 4).apply(integerStream -> integerStream.map(x -> x + 1)).collect(toList()));
    }

    @Test
    void whenFilter() {
        assertEquals(Arrays.asList(2, 4),

                StreamFactory.stream(1, 2, 3, 4).filter(x -> x % 2 == 0)
                        .collect(toList())
        );
    }

    @Test
    void whenMap() {
        assertEquals(Arrays.asList(1D, 4D, 9D, 16D),

                StreamFactory.stream(1, 2, 3, 4).map(x -> Math.pow(x, 2))
                        .collect(toList())
        );
    }


    @Test
    void whenFlatMap() {

        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9),
                StreamFactory.stream(Arrays.asList(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6),
                        Arrays.asList(7, 8, 9)
                )).flatMap(StreamFactory::stream)
                        .collect(toList())
        );
    }

    @Test
    void whenDistinct() {
        assertEquals(Arrays.asList(4, 5, 6, 7),
                StreamFactory.stream(4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7).distinct().collect(toList()));
    }

    @Test
    void whenSorted() {
        assertEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                StreamFactory.stream(3, 5, 2, 4, 1).sorted().collect(toList())
        );
    }

    @Test
    void whenSortedReverseOrder() {
        assertEquals(
                Arrays.asList(5, 4, 3, 2, 1),
                StreamFactory.stream(3, 5, 2, 4, 1).sorted(reverseOrder()).collect(toList())
        );
    }

    @Test
    void peek() {
        Target target = mock(Target.class);

        StreamFactory.stream(1, 2, 3, 4, 5)
                .peek(target::peekMe)
                .size(); // calling terminal op for lazy evaluation

        for (int i = 1; i <= 5; i++) {
            verify(target).peekMe(eq(i));
        }
    }


    @Test
    void takeWhile() {
        assertEquals(Arrays.asList(1, 2, 3, 4),
                StreamFactory.stream(1, 2, 3, 4, 9, 3, 2)
                        .takeWhile(x -> x <= 4)
                        .collect(toList())
        );
    }

    @Test
    void dropWhile() {
        assertEquals(
                Arrays.asList(4, 8, 9, 6),
                StreamFactory.stream(2, 3, 1, 0, -5, 4, 8, 9, 6)
                        .dropWhile(x -> x < 4).collect(toList())
        );
    }

    @Test
    void filterNot() {
        assertEquals(
                Arrays.asList(3, 1, 5),
                StreamFactory.stream(2, 4, 6, 3, 1, 5, 0)
                        .filterNot(x -> x % 2 == 0)
                        .collect(toList())
        );
    }

    @Test
    void filterNulls() {
        assertEquals(
                Arrays.asList(4, 2, 5),
                StreamFactory.stream(4, null, null, 2, null, 5).filterNulls().collect(toList())
        );
    }


    @Test
    void limit() {
        assertEquals(Arrays.asList(1, 2),
                StreamFactory.stream(1, 2, 3, 4, 5).limit(2).collect(toList()));
    }

    @Test
    void skip() {
        assertEquals(Arrays.asList(3, 4, 5),
                StreamFactory.stream(1, 2, 3, 4, 5).skip(2).collect(toList()));
    }

    @Test
    void forEach() {

        Target target = mock(Target.class);
        StreamFactory.stream(1, 2, 3, 4).forEach(target::peekMe);

        for (int i = 1; i <= 4; i++) {
            verify(target).peekMe(eq(i));
        }
    }

    @Test
    void toArray() {
        Integer[] expected = {1, 2, 3};
        assertArrayEquals(expected, StreamFactory.stream(1, 2, 3).toArray(Integer[]::new));
        assertArrayEquals(expected, StreamFactory.stream(1, 2, 3).toArray(Integer[]::new));
    }

    @Test
    void toGenericArray() {
        Integer[] integers = StreamFactory.stream(1, 2, 3).toArray(Integer[]::new);
        assertArrayEquals(new Integer[]{1, 2, 3}, integers);
    }

    @Test
    void reduceWithIdentity() {
        assertEquals(
                10,
                StreamFactory.stream(1, 2, 3, 4).reduce(0, Integer::sum).longValue()
        );
    }


    @Test
    void reduceWithIdentityAndAccumulator() {
        assertEquals(
                10,
                StreamFactory.stream(1, 2, 3, 4).reduce(0,
                        Integer::sum, Integer::sum).longValue()
        );
    }

    @Test
    void reduceForEmpty() {
        assertFalse(
                empty().reduce((x, y) -> y).isPresent());
    }

    @Test
    void reduceForEmptyWithIdentity() {
        assertEquals(1,
                empty().reduce(1, (x, y) -> y).intValue());
    }

    @Test
    void reduceForEmptyWithIdentityAndAccumulator() {
        assertEquals(1,
                empty().reduce(1, (x, y) -> y, (x, y) -> y).intValue());
    }

    private Stream<Integer> empty() {
        return StreamFactory.stream(1).filter(x -> x > 1);
    }

    @Test
    void reduceWithSupplier() {
        Optional<Integer> result = StreamFactory.stream(1, 2, 3, 4).reduce(Integer::sum);
        assertTrue(result.isPresent());
        assertEquals(
                10,
                result.get().intValue()
        );
    }


    @Test
    void collectToSet() {
        assertEquals(Arrays.asList(1, 3, 2, 4), StreamFactory.stream(1, 3, 2, 4).collect(toList()));
    }

    @Test
    void min() {
        Optional<Integer> result = StreamFactory.stream(1, 3, 2, 4).min(Integer::compareTo);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().intValue());

        assertFalse(
                StreamFactory.stream(1, 2, 3).filter(x -> x > 5).min(Integer::compareTo)
                        .isPresent());
    }

    @Test
    void max() {
        Optional<Integer> result = StreamFactory.stream(1, 4, 3, 2).max(Integer::compareTo);
        assertTrue(result.isPresent());
        assertEquals(4, result.get().intValue());

        assertFalse(
                StreamFactory.stream(1, 2, 3).filter(x -> x > 5).max(Integer::compareTo)
                        .isPresent());
    }


    @Test
    void count() {
        assertEquals(3L,
                StreamFactory.stream(1, 2, 3).size());
        assertEquals(0L, StreamFactory.stream(emptyList()).size());
    }

    @Test
    void anyMatch() {
        assertFalse(
                StreamFactory.stream(1, 2, 3).any(x -> x == 5));

        assertTrue(
                StreamFactory.stream(1, 5, 3).any(x -> x == 5));
    }

    @Test
    void allMatch() {
        assertTrue(StreamFactory.empty().all(x -> false));
        assertTrue(StreamFactory.stream(1, 1, 1, 1).all(x -> true));
        assertFalse(StreamFactory.stream(1, 1, 2, 3).all(x -> x == 1));
    }

    @Test
    void noneMatch() {
        assertFalse(StreamFactory.stream(1, 2, 3).none(x -> x == 1));
        assertTrue(StreamFactory.stream(4, 2, 3).none(x -> x == 1));
        assertTrue(StreamFactory.empty().none(x -> true));
    }

    @Test
    void findFirst() {
        Optional<Integer> result = StreamFactory.stream(1, 2, 3).filter(x -> x.equals(2))
                .findOne();

        assertTrue(result.isPresent());
        assertEquals(2, result.get().longValue());

    }

    @Test
    void reverse() {
        List<Integer> stream = StreamFactory.stream(1, 2, 3).reversed().collect(toList());
        assertEquals(3, stream.get(0).intValue());
        assertEquals(2, stream.get(1).intValue());
        assertEquals(1, stream.get(2).intValue());
    }

    @Test
    void intersperse() {

        List<Integer> interspersed = StreamFactory.stream(1,2,3).intersperse(() -> 5).collect(toList());
        assertEquals(
                Arrays.asList(1,5,2,5,3),
                interspersed
        );
    }


    @Test
    void findAnyEmpty() {
        assertFalse(
                empty().findOne().isPresent());
    }

    interface Target {

        void peekMe(Integer x);
    }
}
