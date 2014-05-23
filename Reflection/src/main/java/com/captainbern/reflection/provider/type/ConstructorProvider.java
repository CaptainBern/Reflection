package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;

import java.lang.reflect.Constructor;

public interface ConstructorProvider<T> {

    public Reflection getReflection();

    public <T> SafeConstructor<T> asSafeConstructor();

    public Constructor<T> reflectedConstructor();
}
