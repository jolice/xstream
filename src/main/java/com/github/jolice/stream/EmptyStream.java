package com.github.jolice.stream;

import com.github.jolice.stream.iterator.EmptyIterator;
import lombok.EqualsAndHashCode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.Collector;

@EqualsAndHashCode
public class EmptyStream<T> implements Stream<T> {

    EmptyStream() {
    }

    @Override
    public Stream<T> append(Stream<T> stream) {
        return stream;
    }

    @Override
    public OptionalInt findIndex(Predicate<? super T> predicate) {
        return OptionalInt.empty();
    }

    @Override
    public Iterator<T> iterator() {
        return new EmptyIterator<>();
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return this;
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return this;
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return new EmptyStream<>();
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new EmptyStream<>();
    }

    @Override
    public Stream<T> reversed() {
        return this;
    }

    @Override
    public Stream<T> intersperse(Supplier<T> element) {
        return this;
    }


    @Override
    public Stream<T> distinct() {
        return this;
    }

    @Override
    public Stream<T> apply(UnaryOperator<Stream<T>> op) {
        return op.apply(this);
    }

    @Override
    public Stream<T> sorted() {
        return this;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return this;
    }

    @Override
    public Stream<T> limit(long size) {
        return this;
    }

    @Override
    public Stream<T> skip(long itemCount) {
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        iterator().forEachRemaining(action);
    }

    @Override
    public void forEachIndexed(ObjIntConsumer<? super T> action) {

    }

    @Override
    public T[] toArray(IntFunction<T[]> generator) {
        return generator.apply(0);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return identity;
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return Optional.empty();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return identity;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return collector.finisher().apply(collector.supplier().get());
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean any(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        return true;
    }

    @Override
    public boolean none(Predicate<? super T> predicate) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Optional<T> findOne() {
        return Optional.empty();
    }

}
