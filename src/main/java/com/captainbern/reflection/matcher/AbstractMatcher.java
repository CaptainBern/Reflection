/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.reflection.matcher;

public abstract class AbstractMatcher<T> implements Matcher<T> {

    public Matcher<T> and(final Matcher<? super T> other) {
        return new AndMatcher<T>(this, other);
    }

    public Matcher<T> or(final Matcher<? super T> other) {
         return new OrMatcher<T>(this, other);
    }

    private static class AndMatcher<T> extends AbstractMatcher<T> {

        private Matcher<? super T> parent;
        private Matcher<? super T> other;

        public AndMatcher(Matcher<? super T> parent, Matcher<? super T> other) {
            this.parent = parent;
            this.other = other;
        }

        @Override
        public boolean matches(T type) {
            return parent.matches(type) && other.matches(type);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof AndMatcher
                    && ((AndMatcher) other).parent.equals(parent)
                    && ((AndMatcher) other).other.equals(other);
        }

        @Override
        public String toString() {
            return "and(" + parent + ", " + other + ")";
        }
    }

    private static class OrMatcher<T> extends AbstractMatcher<T> {

        private Matcher<? super T> parent;
        private Matcher<? super T> other;

        public OrMatcher(Matcher<? super T> parent, Matcher<? super T> other) {
            this.parent = parent;
            this.other = other;
        }

        @Override
        public boolean matches(T type) {
            return parent.matches(type) || other.matches(type);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof OrMatcher
                    && ((OrMatcher) other).parent.equals(parent)
                    && ((OrMatcher) other).other.equals(other);
        }

        @Override
        public String toString() {
            return "or(" + parent + ", " + other + ")";
        }
    }
}
