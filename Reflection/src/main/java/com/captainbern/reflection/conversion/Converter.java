package com.captainbern.reflection.conversion;

public interface Converter<TWrapped> {

    public TWrapped getWrapped(Object object);

    public Object getUnwrapped(Class<?> type, TWrapped wrapped);

}
