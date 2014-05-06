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

import com.captainbern.reflection.SafeMethod;

/**
 * An interface for weak-access to methods.
 * @param <T> The return type of the underlying method.
 */
public interface MethodAccessor<T> {

    /**
     * Invokes the underlying method for the given instance with the given arguments.
     * @param instance The instance or null for static methods.
     * @param args The arguments to pass into to instantiate the underlying method.
     * @return The value.
     */
    public T invoke(Object instance, Object... args);

    /**
     * Invokes the underlying method static with the given arguments.
     * @param args The arguments to pass into to instantiate the underlying method.
     * @return The value.
     */
    public T invokeStatic(Object... args);

    /**
     * Returns the underlying method as a ReflectedMethod.
     * @return The underlying method as a ReflectedMethod.
     */
    public SafeMethod getMethod();
}
