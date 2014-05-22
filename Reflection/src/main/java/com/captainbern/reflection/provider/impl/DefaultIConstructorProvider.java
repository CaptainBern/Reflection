package com.captainbern.reflection.provider.impl;

import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.impl.SafeConstructorImpl;
import com.captainbern.reflection.provider.IConstructorProvider;

import java.lang.reflect.Constructor;

public class DefaultIConstructorProvider implements IConstructorProvider {
    @Override
    public <T> SafeConstructor<T> reflect(Constructor<T> constructor) {
        return new SafeConstructorImpl<>(constructor);
    }
}
