package io.riguron.xstream.iterator;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor
public class CombiningIterator<T> implements Iterator<T> {

    private final Iterator<T> first;
    private final Iterator<T> second;

    @Override
    public boolean hasNext() {
        return first.hasNext() || second.hasNext();
    }

    @Override
    public T next() {
        if (first.hasNext()) {
            return first.next();
        }
        return second.next();
    }
}
