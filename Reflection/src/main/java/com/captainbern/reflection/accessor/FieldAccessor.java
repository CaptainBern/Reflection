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

import com.captainbern.reflection.SafeField;

/**
 * An interface for weak access to fields and allows various operations to be done easier.
 * @param <T> The type of the underlying field.
 */
public interface FieldAccessor<T> {

    /**
     * Retrieves the value of the underlying field for the given instance.
     * @param instance The instance or null for a static field.
     * @return The value of the underlying field.
     */
    public T get(Object instance);

    /**
     * Retrieves the value of the underlying field static.
     * @return The value of the underlying field.
     */
    public T getStatic();

    /**
     * Sets the value of the underlying field for the given instance.
     * @param instance The instance or null for a static field.
     * @param value The value that will be assigned to the underlying field.
     */
    public void set(Object instance, T value);

    /**
     * Sets the value of the underlying field static.
     * @param value The value that will be assigned to the underlying field.
     */
    public void setStatic(T value);

    /**
     * Transfers the value of the field of the given instance to the given destination.
     * @param from The object to transfer the field value from.
     * @param to  The object to transfer the field value to.
     */
    public void transfer(Object from, Object to);

    /**
     * Returns the underlying field as a ReflectedField.
     * @return The underlying field as a ReflectedField.
     */
    public SafeField getField();
}
