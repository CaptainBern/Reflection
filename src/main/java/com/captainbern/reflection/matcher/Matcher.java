package com.captainbern.reflection.matcher;

import java.io.Serializable;

public interface Matcher<T> extends Serializable {

    /**
     * Matches the given type. (Return true if it matches, false otherwise)
     * @param type
     * @return
     */
    boolean matches(T type);

    /**
     * Returns true of the if both this and the given matcher return true.
     * @param otherMatcher
     * @return
     */
    Matcher<T> and(final Matcher<? super T> otherMatcher);

    /**
     * Returns true if this or the given matcher return true.
     * @param otherMatcher
     * @return
     */
    Matcher<T> or(final Matcher<? super T> otherMatcher);
}
