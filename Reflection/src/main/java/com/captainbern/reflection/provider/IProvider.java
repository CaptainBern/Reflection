package com.captainbern.reflection.provider;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.SafeMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IProvider {

    public <T> ClassTemplate<T> reflect(final Class<T> clazz);

    public <T> SafeField<T> reflect(final Field field);

    public <T> SafeConstructor<T> reflect(final Constructor<T> constructor);

    public <T> SafeMethod<T> reflect(final Method method);
}
