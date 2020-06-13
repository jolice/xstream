package com.github.jolice.stream;

import com.github.jolice.stream.comparator.ArbitraryTypeComparator;
import com.github.jolice.stream.comparator.Extreme;
import com.github.jolice.stream.comparator.InverseComparator;
import com.github.jolice.stream.iterator.*;
import com.github.jolice.stream.matcher.AllMatch;
import com.github.jolice.stream.matcher.AnyMatch;
import com.github.jolice.stream.matcher.Match;
import com.github.jolice.stream.matcher.NoneMatch;
import com.github.jolice.stream.operation.ElementCount;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

public class ElementStream<T> implements Stream<T> {


    private Iterator<T> elementProvider;

    ElementStream(Iterator<T> elementProvider) {
        this.elementProvider = elementProvider;
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return this.decorate(new FilteringIterator<>(elementProvider, predicate));
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return this.decorate(new TakeWhileIterator<>(this.elementProvider, predicate));
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return this.decorate(new DropWhileIterator<>(this.elementProvider, predicate));
    }

    @Override
    public Stream<T> distinct() {
        return new ElementStream<>(new DistinctIterator<>(elementProvider));
    }

    @Override
    public Stream<T> apply(UnaryOperator<Stream<T>> op) {
        return op.apply(this);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return this.decorate(new MappingIterator<>(elementProvider, mapper));
    }


    @Override
    public void forEach(Consumer<? super T> action) {
        while (elementProvider.hasNext()) {
            action.accept(elementProvider.next());
        }
    }

    @Override
    public void forEachIndexed(ObjIntConsumer<? super T> action) {
        int index = 0;
        while (elementProvider.hasNext()) {
            action.accept(elementProvider.next(), index++);
        }
    }

    @Override
    public Stream<T> sorted() {
        return sorted(new ArbitraryTypeComparator<>());
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return this.decorate(new PeekingIterator<>(this.elementProvider, action));
    }

    @Override
    public Stream<T> limit(long size) {
        return this.decorate(new LimitedIterator<>(this.elementProvider, size));
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return this.decorate(new SortedIterator<>(this.elementProvider, comparator));
    }

    @Override
    public Stream<T> skip(long itemCount) {
        return new ElementStream<>(new SkippingIterator<>(this.elementProvider, itemCount));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return new Extreme<>(elementProvider, comparator).get();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return new Extreme<>(this.elementProvider, new InverseComparator<>(comparator)).get();
    }

    @Override
    public Stream<T> append(Stream<T> stream) {
        return this.decorate(
                new CombiningIterator<>(this.elementProvider, stream.iterator())
        );
    }

    @Override
    public OptionalInt findIndex(Predicate<? super T> predicate) {
        OptionalInt result = OptionalInt.empty();
        int index = 0;
        while (elementProvider.hasNext()) {
            if (predicate.test(elementProvider.next())) {
                return OptionalInt.of(index);
            }
            index++;
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return this.elementProvider;
    }

    @Override
    public int size() {
        return new ElementCount(iterator()).evaluate();
    }

    @Override
    public boolean any(Predicate<? super T> predicate) {
        return evaluateMatch(AnyMatch::new, predicate);
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        return evaluateMatch(AllMatch::new, predicate);
    }

    @Override
    public boolean none(Predicate<? super T> predicate) {
        return evaluateMatch(NoneMatch::new, predicate);
    }

    @Override
    public boolean isEmpty() {
        return !elementProvider.hasNext();
    }

    private <R extends Match<T>> boolean evaluateMatch(BiFunction<Iterator<T>, Predicate<? super T>, R> producer, Predicate<? super T> predicate) {
        return producer.apply(this.elementProvider, predicate).evaluate();
    }

    @Override
    public Optional<T> findOne() {
        return elementProvider.hasNext() ? Optional.ofNullable(elementProvider.next()) : Optional.empty();
    }

    @Override
    public T[] toArray(IntFunction<T[]> generator) {
        List<T> list = collect(toList());
        T[] result = generator.apply(list.size());
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return this.decorate(new FlatMappingIterator<>(mapper, elementProvider));
    }

    @Override
    public Stream<T> reversed() {
        return this.decorate(new ReverseIterator<>(this.elementProvider));
    }

    @Override
    public Stream<T> intersperse(Supplier<T> element) {
        return this.decorate(new InterspersingIterator<>(this.elementProvider, element.get()));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        if (!elementProvider.hasNext()) {
            return identity;
        }
        T initial = accumulator.apply(identity, elementProvider.next());
        while (elementProvider.hasNext()) {
            initial = accumulator.apply(initial, elementProvider.next());
        }
        return initial;
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return !elementProvider.hasNext() ? Optional.empty() : Optional.of(this.reduce(elementProvider.next(), accumulator));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {

        if (!elementProvider.hasNext()) {
            return identity;
        }

        U initial = accumulator.apply(identity, elementProvider.next());
        while (elementProvider.hasNext()) {
            U item = accumulator.apply(identity, elementProvider.next());
            initial = combiner.apply(item, initial);
        }
        return initial;

    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        A container = collector.supplier().get();
        while (elementProvider.hasNext()) {
            T next = elementProvider.next();
            BiConsumer<A, ? super T> accumulator = collector.accumulator();
            accumulator.accept(container, next);
        }
        return collector.finisher().apply(container);
    }

    private <V> Stream<V> decorate(Iterator<V> iterator) {
        return new ElementStream<>(iterator);
    }


}
