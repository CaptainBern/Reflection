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

package com.captainbern.reflection.accessor;

import com.captainbern.reflection.SafeConstructor;

/**
 * An interface for weak-access to constructors and make their invocation easier.
 * @param <T> The type of the underlying constructor.
 */
public interface ConstructorAccessor<T> {

    /**
     * Invokes the underlying constructor with the given arguments.
     * @param args The arguments used to instantiate the constructor.
     * @return The return value.
     */
    public T invoke(Object... args);

    /**
     * Returns the underlying constructor as a ReflectedConstructor.
     * @return The underlying constructor as a ReflectedConstructor.
     */
    public SafeConstructor getConstructor();
}
