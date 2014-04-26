package com.captainbern.reflection.matcher;

public abstract class Matcher<T> {

    public abstract boolean matches(Object parent, T value);

    public Matcher<T> and(final Matcher<T> other) {
        return new Matcher<T>() {
            @Override
            public boolean matches(Object parent, T value) {
                return this.matches(parent, value) && other.matches(parent, value);
            }
        };
    }

    public Matcher<T> or(final Matcher<T> other) {
        return new Matcher<T>() {
            @Override
            public boolean matches(Object parent, T value) {
                return this.matches(parent, value) || other.matches(parent, value);
            }
        };
    }
}
