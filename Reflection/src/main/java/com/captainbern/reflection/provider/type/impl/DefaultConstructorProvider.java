package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.impl.SafeConstructorImpl;
import com.captainbern.reflection.provider.type.ConstructorProvider;

import java.lang.reflect.Constructor;

@SuppressWarnings("unchecked")
public class DefaultConstructorProvider<T> implements ConstructorProvider<T> {

    private final Reflection reflection;
    private final Constructor constructor;

    public DefaultConstructorProvider(final Reflection reflection, final Constructor<T> constructor) {
        this.reflection = reflection;
        this.constructor = constructor;
    }

    @Override
    public Reflection getReflection() {
        return this.reflection;
    }

    @Override
    public SafeConstructor<T> asSafeConstructor() {
        return new SafeConstructorImpl<T>(this.reflection, this.constructor);
    }

    @Override
    public Constructor<T> reflectedConstructor() {
        return this.constructor;
    }
}
