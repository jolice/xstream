package io.riguron.xstream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface Stream<T> {

    /**
     * Appends target stream to a current one producing a stream containing
     * items from both streams (stream concatenation).
     *
     * @param stream stream to append to a current
     * @return current stream concatenated with a provided
     */
    Stream<T> append(Stream<T> stream);

    /**
     * Finds an index of a specified item relatively to the other
     * items in current stream.
     *
     * @param predicate matching condition for the item to be located
     * @return optional wrapping an index, or an empty optional if a stream doesn't contain an
     * element matching specified predicate.
     */
    OptionalInt findIndex(Predicate<? super T> predicate);

    /**
     * An iterator of the current stream.
     *
     * @return iterator producing elements of the current stream
     */
    Iterator<T> iterator();

    /**
     * Produces a stream omitting the items from the current one
     * that don't match the specified predicate.
     *
     * @param predicate filter
     * @return stream filtered by the predicate
     */
    Stream<T> filter(Predicate<? super T> predicate);

    /**
     * Inverse for the filter.
     *
     * @param predicate filter
     * @return stream filtered by the predicate
     */
    default Stream<T> filterNot(Predicate<? super T> predicate) {
        return this.filter(predicate.negate());
    }

    /**
     * Truncates current stream to the first element that doesn't match
     * the predicate.
     *
     * @param predicate filter
     * @return truncated stream
     */
    Stream<T> takeWhile(Predicate<? super T> predicate);

    /**
     * Omits elements from the current stream unto the first element
     * matching the predicate
     *
     * @param predicate filter
     * @return truncated  stream
     */
    Stream<T> dropWhile(Predicate<? super T> predicate);

    /**
     * Classic list mapping operation.
     *
     * @param mapper element mapper
     * @param <R>    new stream type
     * @return new stream containing elements produced by applying the
     * function to the each element of the current stream
     */
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    /**
     * Flattens multiple streams into a single one.
     *
     * @param mapper flattening function
     * @param <R>    new stream type
     * @return stream constructed from the results of the function application to
     * the each element of the current stream.
     */
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    /**
     * Reverses elements in a current stream.
     *
     * @return reversed stream
     */
    Stream<T> reversed();

    /**
     * Inserts an element after each element in a current stream.
     *
     * @param element element to insert
     * @return stream interspersed by an element
     */
    Stream<T> intersperse(Supplier<T> element);

    /**
     * Alias for the intersperse method passing
     * element directly instead of the supplier.
     *
     * @param item element to insert
     * @return stream interspersed by an element
     */
    default Stream<T> intersperse(T item) {
        return intersperse(() -> item);
    }

    /**
     * Eliminates duplicates from the current stream (in terms the equals() equality).
     *
     * @return stream omitting duplicates
     */
    Stream<T> distinct();

    /**
     * Applies a custom abstract function to the current stream.
     *
     * @param op an operation
     * @return stream produced by applying the operation to a current stream
     */
    Stream<T> apply(UnaryOperator<Stream<T>> op);

    /**
     * Filters nulls from a current stream, i.e produces new stream from the current one dropping
     * any null items.
     *
     * @return current stream, but without nulls
     */
    default Stream<T> filterNulls() {
        return filter(Objects::nonNull);
    }

    /**
     * Sorts elements in a current stream naturally.
     *
     * @return sorted stream
     * @throws IllegalArgumentException if stream elements are incomparable (i.e don't implement an appropriate interface).
     */
    Stream<T> sorted();

    /**
     * Sorts elements in a current stream by a specified comparator.
     *
     * @param comparator element comparator
     * @return sorted stream
     */
    Stream<T> sorted(Comparator<? super T> comparator);

    /**
     * Sorts elements in a current stream by a specific element property.
     *
     * @param propertyReader sorting property provider
     * @param <U>            type of the sorting property
     * @return sorted a stream
     */
    default <U extends Comparable<? super U>> Stream<T> sortBy(Function<? super T, ? extends U> propertyReader) {
        return sorted(Comparator.comparing(propertyReader));
    }

    /**
     * Performs an action on each element of the current stream.
     *
     * @param action action to perform
     * @return current stream with an stream processing action bound to an each element
     */
    Stream<T> peek(Consumer<? super T> action);

    /**
     * Truncates stream to a certain count of the items, i.e
     * limits the pipeline size.
     *
     * @param size truncation length
     * @return stream truncated to a certain number of items
     */
    Stream<T> limit(long size);

    /**
     * Omits first N items, i.e truncates current stream starting with Nth element.
     *
     * @param itemCount number of items to skip
     * @return stream truncated starting with Nth element
     */
    Stream<T> skip(long itemCount);

    /**
     * Iterates over the items and applies an action to each one.
     *
     * @param action an action to apply to each item in the stream
     */
    void forEach(Consumer<? super T> action);

    /**
     * Iterates over the items and applies an action to each one, passing
     * an item index as well.
     *
     * @param action an action to apply to each item in the stream
     */
    void forEachIndexed(ObjIntConsumer<? super T> action);

    /**
     * Dumps stream contents to an array.
     *
     * @param generator function responsible for creating an output array
     * @return array containing stream items
     */
    T[] toArray(IntFunction<T[]> generator);

    /**
     * Reduces stream items with accordance to the given function.
     *
     * @param identity    initial item
     * @param accumulator function applied to the each stream item to obtain a final result
     * @return result of stream reduction
     */
    T reduce(T identity, BinaryOperator<T> accumulator);

    /**
     * Reduces stream items with accordance to the given function using first item as an initial value.
     *
     * @param accumulator function applied to the each stream item to obtain a final result
     * @return result of stream reduction
     */
    Optional<T> reduce(BinaryOperator<T> accumulator);

    /**
     * Reduces stream items with accordance to the given function and a specified combining function.
     *
     * @param accumulator function applied to the each stream item to obtain a final result
     * @return result of stream reduction
     */
    <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);

    /**
     * Dumps stream items into a collection
     *
     * @param collector collecting function
     * @param <R>       type of the resulting collection
     * @return collection containing stream items
     */
    <R, A> R collect(Collector<? super T, A, R> collector);

    /**
     * Computes the greatest (by a specific field) element in the stream.
     *
     * @param propertyReader ordering field value producer
     * @return greatest item in a stream
     */
    default <U extends Comparable<? super U>> Optional<T> maxBy(Function<? super T, ? extends U> propertyReader) {
        return max(Comparator.comparing(propertyReader));
    }

    /**
     * Computes the least (by a specific field) element in the stream.
     *
     * @param propertyReader ordering field value producer
     * @return least item in a stream
     */
    default <U extends Comparable<? super U>> Optional<T> minBy(Function<? super T, ? extends U> propertyReader) {
        return max(Comparator.comparing(propertyReader));
    }


    /**
     * Computes the least item in a stream.
     *
     * @param comparator item comparing function
     * @return least value in the stream
     */
    Optional<T> min(Comparator<? super T> comparator);

    /**
     * Computes the greatest item in a stream.
     *
     * @param comparator item comparing function
     * @return greatest value in the stream
     */
    Optional<T> max(Comparator<? super T> comparator);

    /**
     * Length of the stream.
     *
     * @return count of items in a stream
     */
    int size();

    /**
     * Evaluates whether a stream contains an item matching
     * a certain condition.
     * A result for an empty stream is always false.
     *
     * @param predicate a condition
     * @return where any item in a stream matches a certain condition.
     */
    boolean any(Predicate<? super T> predicate);

    /**
     * Evaluates whether all items in a stream match a certain condition.
     * A result for an empty stream is always true.
     *
     * @param predicate a condition
     * @return where any item in a stream matches a certain condition.
     */
    boolean all(Predicate<? super T> predicate);

    /**
     * Evaluates whether none of the items in a stream match a certain condition.
     * A result for an empty stream is always true.
     *
     * @param predicate a condition
     * @return where any item in a stream matches a certain condition.
     */
    boolean none(Predicate<? super T> predicate);

    /**
     * Whether the stream contains no items.
     *
     * @return whether the stream is empty.
     */
    boolean isEmpty();

    /**
     * Whether the stream is not empty.
     *
     * @return whether the stream contains at least one items
     */
    default boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Returns a first item in the stream.
     *
     * @return first item in the stream, or empty optional if stream is empty.
     */
    Optional<T> findOne();

    /**
     * Finds an element matching a certain condition.
     *
     * @param predicate condition
     * @return first element in a stream matching a predicate, or
     * empty optional if the stream is empty or no items matching
     * a condition are found
     */
    default Optional<T> find(Predicate<? super T> predicate) {
        return this.filter(predicate).findOne();
    }

    /**
     * Finds all elements matching a certain condition.
     *
     * @param predicate condition
     * @return all elements in a stream matching a predicate.
     */
    default List<T> findAll(Predicate<? super T> predicate) {
        return this.filter(predicate).list();
    }

    /**
     * Terminates a stream dumping its contents to a list.
     *
     * @return list representing stream contents
     */

    default List<T> list() {
        return collect(Collectors.toList());
    }

    /**
     * Terminates a stream dumping its contents to a set.
     *
     * @return set representing stream contents
     */
    default Set<T> set() {
        return collect(Collectors.toSet());
    }

}
