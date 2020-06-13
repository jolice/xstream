package io.riguron.xstream.matcher;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * The common stream matching operations can be represented as follows:
 *
 *  all-match()
 *     for (x <- xs)
 *       if (!match x)
 *          false
 *  true

 *  none-match()
 *     for (x <- xs)
 *       if (match x)
 *          false
 *  true

 *  any-match()
 *     for (x <- xs)
 *       if (match x)
 *          true
 *  false
 *
 * All the loops have the same structure and only differ in:
 *  - Whether the result of matching is inverted (if match x/ if !match x)
 *  - The value returned in case of the matching result (return true/false or false/true when the matching condition is met
 *  and right after the loop)
 *
 * Thus, the general algorithm may be represented as follows:
 *
 * match(invert, matched)
 *   for (x <- xs)
 *      if (match x) ^ invert
 *         matched
 *   !matched
 *
 * I'd recall the table of ^ (inverse) operator application:
 *
 * - x ^ false -> x, namely:
 *   - false ^ false -> false
 *   - true ^ false -> false
 *
 * - false ^ true -> true (false inverted)
 * - true ^ true -> false (true inverted)
 *
 * @param <T> type of the elements in a stream
 */
@RequiredArgsConstructor
public class ListMatch<T> implements Match<T> {

    private final Iterator<T> iterator;
    private final Predicate<? super T> match;
    private final Matching matching;

    @Override
    public boolean evaluate() {
        final boolean whenAccepted = matching.whenMatches();
        while (iterator.hasNext()) {

            boolean matchResult = match.test(iterator.next());
            if (matching.inverseMatchingResult()) {
                matchResult = !matchResult;
            }

            if (matchResult) {
                return whenAccepted;
            }
        }
        return !whenAccepted;
    }


}
