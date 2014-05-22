package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.impl.SafeConstructorImpl;
import com.captainbern.reflection.provider.type.ConstructorProvider;

import java.lang.reflect.Constructor;

public class DefaultConstructorProvider<T> implements ConstructorProvider<T> {

    private final Constructor constructor;

    public DefaultConstructorProvider(final Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public <T> SafeConstructor<T> asSafeConstructor() {
        return new SafeConstructorImpl<T>(this.constructor);
    }

    @Override
    public Constructor<T> reflectedConstructor() {
        return this.constructor;
    }
}
