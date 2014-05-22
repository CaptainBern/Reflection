package com.captainbern.reflection.provider;

import com.captainbern.reflection.provider.type.ClassProvider;
import com.captainbern.reflection.provider.type.ConstructorProvider;
import com.captainbern.reflection.provider.type.FieldProvider;
import com.captainbern.reflection.provider.type.MethodProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReflectionProvider {

    public <T> ClassProvider<T> getClassProvider(final Class<T> clazz, final boolean forceAccess);

    public <T> ClassProvider<T> getClassProvider(final String className, final boolean forceAccess);

    public <T> ConstructorProvider<T> getConstructorProvider(final Constructor<T> constructor);

    public <T> ConstructorProvider<T> getConstructorProvider(final Class<T> clazz, final Class... args);

    public <T> FieldProvider<T> getFieldProvider(final Field field);

    public <T> FieldProvider<T> getFieldProvider(final Class<?> clazz, final String fieldName);

    public <T> MethodProvider<T> getMethodProvider(final Method method);

    public <T> MethodProvider<T> getMethodProvider(final Class<?> clazz, final String methodName, final Class... args);
}
