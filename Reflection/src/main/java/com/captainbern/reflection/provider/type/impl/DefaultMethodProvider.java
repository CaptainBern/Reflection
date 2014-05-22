package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.impl.SafeMethodImpl;
import com.captainbern.reflection.provider.type.MethodProvider;

import java.lang.reflect.Method;

public class DefaultMethodProvider<T> implements MethodProvider<T> {

    private final Method method;

    public DefaultMethodProvider(final Method method) {
        this.method = method;
    }

    @Override
    public <T> SafeMethod<T> asSafeMethod() {
        return new SafeMethodImpl<T>(this.method);
    }

    @Override
    public Method reflectedMethod() {
        return null;
    }
}
