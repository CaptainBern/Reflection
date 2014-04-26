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
