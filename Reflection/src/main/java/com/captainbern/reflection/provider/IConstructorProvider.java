package com.captainbern.reflection.provider;

import com.captainbern.reflection.SafeConstructor;

import java.lang.reflect.Constructor;

public interface IConstructorProvider {

    public <T> SafeConstructor<T> reflect(Constructor<T> constructor);
}
