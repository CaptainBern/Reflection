package com.captainbern.reflection.accessor;

import com.captainbern.reflection.ReflectedConstructor;

public interface ConstructorAccessor {

    /**
     * Invokes the constructor with the given arguments.
     * @param args
     * @return
     */
    public Object invoke(Object... args);

    /**
     * Returns the constructor as a ReflectedConstructor.
     * @return
     */
    public ReflectedConstructor getConstructor();
}
