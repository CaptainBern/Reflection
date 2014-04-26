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
