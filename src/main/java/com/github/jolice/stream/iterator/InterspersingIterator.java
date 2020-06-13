package com.github.jolice.stream.iterator;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor
public class InterspersingIterator<T> implements Iterator<T> {

    private final Iterator<T> delegate;
    private final T value;
    private int counter;

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public T next() {
        return counter++ % 2 == 0 ? delegate.next() : value;
    }
}
