package com.captainbern.reflection.provider.impl;

import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.impl.SafeMethodImpl;
import com.captainbern.reflection.provider.IMethodProvider;

import java.lang.reflect.Method;

public class DefaultMethodProvider implements IMethodProvider {

    @Override
    public <T> SafeMethod<T> reflect(Method method) {
        return new SafeMethodImpl<>(method);
    }
}
