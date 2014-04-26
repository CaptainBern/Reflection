package com.captainbern.reflection;

import java.util.List;

/**
 * @author CaptainBern
 */
public interface ReflectedClass<T> extends Access<T> {

    /**
     * Returns the Descriptor of this class.
     * @return
     */
    public String getDescriptor();

    /**
     * Constructs a new instance of this class using the given arguments.
     * @param args
     * @return
     */
    public ReflectedObject<T> newInstance(Object... args);

    /**
     * Returns a List of all the Super classes of this class.
     * @return
     */
    public List<ReflectedClass> getSuperClasses();
}
