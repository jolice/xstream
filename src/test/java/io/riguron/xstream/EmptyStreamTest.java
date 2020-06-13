package io.riguron.xstream;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmptyStreamTest {


    @Test
    void iterator() {
        assertFalse(emptyStream().iterator().hasNext());
    }

    @Test
    void filter() {
        assertSelf(x -> x.filter(y -> y == 5));
    }

    @Test
    void filterNot() {
        assertSelf(x -> x.filterNot(y -> y == 5));
    }

    @Test
    void takeWhile() {
        assertSelf(x -> x.takeWhile(y -> y == 5));
    }

    @Test
    void dropWhile() {
        assertSelf(x -> x.dropWhile(y -> y == 5));
    }

    @Test
    void map() {
        assertSelf(x -> x.map(y -> y + 1));
    }

    @Test
    void flatMap() {
        assertSelf(x -> x.flatMap(y -> emptyStream()));
    }

    @Test
    void distinct() {
        assertSelf(Stream::distinct);
    }

    @Test
    void apply() {
        assertSelf(x -> x.apply(y -> y));
    }

    @Test
    void sorted() {
        assertSelf(x -> x.sorted((o1, o2) -> 0));
    }

    @Test
    void testSorted() {
        assertSelf(Stream::sorted);
    }

    @Test
    void peek() {
        assertSelf(x -> x.peek(y -> {
        }));
    }

    @Test
    void limit() {
        assertSelf(x -> x.limit(5));
    }

    @Test
    void skip() {
        assertSelf(x -> x.skip(5));
    }

    @Test
    void forEach() {
        Consumer<Integer> consumer = mock(Consumer.class);
        emptyStream().forEach(consumer);
        verifyZeroInteractions(consumer);
    }

    @Test
    void toArray() {
        assertEquals(
                0, new EmptyStream<Integer>().toArray(Integer[]::new).length
        );
    }

    @Test
    void testToArray() {
        assertEquals(0,
                emptyStream().toArray(Integer[]::new).length);
    }

    @Test
    void reduce() {
        assertEquals(5,
                new EmptyStream<Integer>().reduce(5, (x, y) -> y).intValue());
    }

    @Test
    void reduceBinaryOpAccumulator() {
        assertFalse(
                emptyStream().reduce((x, y) -> y).isPresent()
        );
    }

    @Test
    void reduce3Args() {
        Integer identity = 5;
        assertSame(identity, emptyStream().reduce(identity, (x, y) -> Integer.MAX_VALUE, (x, y) -> Integer.MAX_VALUE));
    }

    @Test
    void collectCollector() {
        assertTrue(
                emptyStream().collect(toList()).isEmpty());
    }

    @Test
    void min() {
        assertFalse(emptyStream().min(Comparator.comparingInt(x -> 1)).isPresent());
    }

    @Test
    void max() {
        assertFalse(emptyStream().max(Comparator.comparingInt(x -> 1)).isPresent());
    }

    @Test
    void count() {
        assertEquals(0, emptyStream().size());
    }

    @Test
    void anyMatch() {
        assertFalse(emptyStream().any(x -> true));
    }

    @Test
    void allMatch() {
        assertTrue(emptyStream().none(x -> true));
    }

    @Test
    void noneMatch() {
        assertTrue(emptyStream().none(x -> true));
    }

    @Test
    void findAny() {
        assertFalse(emptyStream().findOne().isPresent());
    }


    private EmptyStream<Integer> emptyStream() {
        return new EmptyStream<>();
    }

    private void assertSelf(UnaryOperator<Stream<Integer>> op) {
        EmptyStream<Integer> stream = emptyStream();
        assertEquals(stream, op.apply(stream));
    }
}

