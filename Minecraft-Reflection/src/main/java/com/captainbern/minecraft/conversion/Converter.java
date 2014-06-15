package com.captainbern.minecraft.conversion;

public interface Converter<T> {

    public T getWrapped(Class<?> clazz);

    public Object getUnWrapped(Class<?> type, T wrapperType);

}
