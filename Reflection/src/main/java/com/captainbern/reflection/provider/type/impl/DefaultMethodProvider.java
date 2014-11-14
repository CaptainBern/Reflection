package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.impl.SafeMethodImpl;
import com.captainbern.reflection.provider.type.MethodProvider;

import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class DefaultMethodProvider<T> implements MethodProvider<T> {

    private final Reflection reflection;
    private final Method method;

    public DefaultMethodProvider(final Reflection reflection, final Method method) {
        this.reflection = reflection;
        this.method = method;
    }

    @Override
    public Reflection getReflection() {
        return this.reflection;
    }

    @Override
    public SafeMethod<T> asSafeMethod() {
        return new SafeMethodImpl<T>(this.reflection, this.method);
    }

    @Override
    public Method reflectedMethod() {
        return this.method;
    }
}
