package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.SafeConstructor;

import java.lang.reflect.Constructor;

public interface ConstructorProvider<T> {

    public <T> SafeConstructor<T> asSafeConstructor();

    public Constructor<T> reflectedConstructor();
}
