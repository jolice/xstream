package io.riguron.xstream;

import io.riguron.xstream.iterator.ArrayIterator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.function.UnaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamFactory {

    @SafeVarargs
    public static <T> Stream<T> stream(T... items) {
        return items.length == 0 ? empty() : makeStream(new ArrayIterator<>(items));
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return makeStream(iterator);
        } else {
            return empty();
        }
    }

    public static <T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) {
        return makeStream(new Iterator<T>() {

            private T t;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return t == null ? (t = seed) : (t = f.apply(t));
            }
        });
    }

    public static <T> Stream<T> empty() {
        return new EmptyStream<>();
    }

    private static <T> Stream<T> makeStream(Iterator<T> iterator) {
        return new ElementStream<>(iterator);
    }
}
