# Stream
Implementation of simplified version of Java Stream API with lazy evaluation.

# Creating a stream

StreamFactory class is responsible for creating streams. 

A stream may be constructed from fixed list of elements:

```java
Stream<String> stream = StreamFactory.of("alpha", "bravo", "charlie", "delta", "echo");
```

Or from any Iterable implementation:

```java
List<String> list = Arrays.asList("alpha", "bravo", "charlie", "delta", "echo");
Stream<String> stream = StreamFactory.of(list);
```

Empty stream may be created as follows:

```java
Stream<String> stream = StreamFactory.empty();
```

Infinite streams are also supported:

```java
Stream<Integer> stream = StreamFactory.iterate(1, i -> i + 1).limit(5);
```

# Operations

- Iterator
- Filter
- Map
- FlatMap
- Distinct
- Sorted
- Peek
- Limit
- Skip
- ForEach
- ToArray
- Reduce
- Collect
- Min
- Max
- Count
- AnyMatch
- AllMatch
- NoneMatch
- FindAny 

The following operations are not supported:

- Parallel
- MapToInt / Double / Long
- FlatMapToInt / Double / Long
- ForEachOrdered (since stream is not parallel)
