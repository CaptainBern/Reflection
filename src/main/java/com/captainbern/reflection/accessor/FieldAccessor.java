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

import com.captainbern.reflection.ReflectedField;

public interface FieldAccessor<T> {

    /**
     * Retrieves the value of a field for the given instance.
     * @param instance
     * @return
     */
    public T get(Object instance);

    /**
     * Sets the value of a field for the given instance.
     * @param instance
     * @param value
     */
    public void set(Object instance, T value);

    /**
     * Transfers the value of the field of the given instance to the given destination.
     * @param from
     * @param to
     */
    public void transfer(Object from, Object to);

    /**
     * Returns the ReflectedField object of this accessor.
     * @return
     */
    public ReflectedField getField();
}
