package io.riguron.xstream.matcher;

import java.util.Iterator;
import java.util.function.Predicate;

public class NoneMatch<T> extends ListMatch<T> {

    public NoneMatch(Iterator<T> iterator, Predicate<? super T> match) {
        super(iterator, match, new Matching() {
            @Override
            public boolean inverseMatchingResult() {
                return false;
            }

            @Override
            public boolean whenMatches() {
                return false;
            }
        });
    }

}
