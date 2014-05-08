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

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Matchers {

    /**
     * No you can't.
     */
    private Matchers() {
        super();
    }

    public static Matcher<Member> withModifier(final int modifiers) {
        return new AbstractMatcher<Member>() {
            @Override
            public boolean matches(Member type) {
                return (type.getModifiers() & modifiers) == modifiers;
            }
        };
    }

    public static Matcher<Member> withBannedModifiers(final int modifiers) {
        return new AbstractMatcher<Member>() {
            @Override
            public boolean matches(Member type) {
                return(type.getModifiers() & modifiers) == 0;
            }
        };
    }

    public static Matcher<Member> withNameRegex(final Pattern pattern) {
        return new AbstractMatcher<Member>() {
            @Override
            public boolean matches(Member type) {
                return pattern.matcher(type.getName()).matches();
            }
        };
    }

    public static Matcher<Member> withNameRegex(final String name) {
        return withNameRegex(Pattern.compile(name));
    }

    public static Matcher<Member> withExactName(final String name) {
        return withNameRegex(Pattern.quote(name));
    }

    public static Matcher<Method> withArgumentCount(final int argumentCount) {
        return new AbstractMatcher<Method>() {
            @Override
            public boolean matches(Method type) {
                return type.getParameterCount() == argumentCount;
            }
        };
    }

    public static Matcher<Method> withArguments(final Class[] arguments) {
        return new AbstractMatcher<Method>() {
            @Override
            public boolean matches(Method type) {
                return Arrays.equals(type.getParameterTypes(), arguments);
            }
        };
    }

    public static Matcher<Method> withReturnType(final Class<?> returnType) {
        return new AbstractMatcher<Method>() {
            @Override
            public boolean matches(Method type) {
                return type.getReturnType().equals(returnType);
            }
        };
    }

    public static <T> Matcher<T> and(final Matcher<? super T> parent, final Matcher<? super T> other) {
        return new AndMatcher<>(parent, other);
    }

    public static <T> Matcher<T> or(final Matcher<? super T> parent, final Matcher<? super T> other) {
        return new OrMatcher<>(parent, other);
    }

    public static <T> Matcher<T> combine(final List<Matcher<? super T>> matchers){
        return new CombinedMatcher<>(matchers);
    }

    /**
     * Different matchers
     */

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

    private static class CombinedMatcher<T> extends AbstractMatcher<T> {

        protected List<Matcher<? super T>> matchers;

        public CombinedMatcher(List<Matcher<? super T>> matcherList) {
            this.matchers = matcherList;
        }

        @Override
        public boolean matches(T type) {
            for(int i = 0; i < this.matchers.size(); i++) {
                if(!this.matchers.get(i).matches(type))
                    return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof CombinedMatcher
                    && ((CombinedMatcher) other).matchers.equals(this.matchers);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("combined");
            int index = 0;
            while(index < this.matchers.size()) {
                builder.append("(");
                while (index < this.matchers.size()) {
                    builder.append(", ");
                    builder.append(this.matchers.get(index++));
                }
            }
            return builder.toString();
        }
    }
}
