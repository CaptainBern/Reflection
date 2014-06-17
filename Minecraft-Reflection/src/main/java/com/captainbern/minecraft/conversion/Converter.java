package com.captainbern.minecraft.conversion;

public interface Converter<T> {

    public T getWrapped(Object object);

    public Object getUnWrapped(Class<?> type, T wrapperType);

}
