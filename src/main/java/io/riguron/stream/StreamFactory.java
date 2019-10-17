package io.riguron.stream;

import io.riguron.stream.iterator.ArrayIterator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Iterator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamFactory {

    @SafeVarargs
    public static <T> Stream<T> of(T... items) {
        return items.length == 0 ? empty() : makeStream(new ArrayIterator<>(items));
    }

    public static <T> Stream<T> of(Iterable<T> iterable) {
        return makeStream(iterable.iterator());
    }

    public static <T> Stream<T> empty() {
        return new EmptyStream<>();
    }

    public static <T> Stream<T> of(Collection<T> items) {
        return items.isEmpty() ? empty() : makeStream(items.iterator());
    }

    private static <T> Stream<T> makeStream(Iterator<T> iterator) {
        return new ElementStream<>(iterator);
    }
}
