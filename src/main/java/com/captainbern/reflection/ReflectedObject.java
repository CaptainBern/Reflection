package com.captainbern.reflection;

/**
 * @author CaptainBern
 */
public interface ReflectedObject<T> extends Access<T> {

    public ReflectedClass<T> asReflectedClass();

    public T value();

    public <C> C cast(Class<C> type);
}
