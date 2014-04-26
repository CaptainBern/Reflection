package com.captainbern.reflection.accessor;

import com.captainbern.reflection.ReflectedMethod;

public interface MethodAccessor<T> {

    /**
     * Invokes the method for the given instance with the given arguments.
     * @param instance
     * @param args
     * @return
     */
    public T invoke(Object instance, Object... args);

    /**
     * Returns the method as a ReflectedMethod.
     * @return
     */
    public ReflectedMethod getMethod();
}
