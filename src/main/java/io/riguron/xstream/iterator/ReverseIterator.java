package io.riguron.xstream.iterator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class ReverseIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private boolean created = false;

    public ReverseIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator().hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

    private Iterator<T> iterator() {
        if (!created) {
            Deque<T> reversed = new ArrayDeque<>();
            while (iterator.hasNext()) {
                reversed.addFirst(iterator.next());
            }
            this.iterator = reversed.iterator();
            this.created = true;
        }
        return iterator;
    }
}
