package com.captainbern.reflection.provider;

import com.captainbern.reflection.EnumModifier;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.provider.type.ClassProvider;
import com.captainbern.reflection.provider.type.ConstructorProvider;
import com.captainbern.reflection.provider.type.FieldProvider;
import com.captainbern.reflection.provider.type.MethodProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReflectionProvider {

    public <T> ClassProvider<T> getClassProvider(final Reflection reflection, final Class<T> clazz, final boolean forceAccess);

    public <T> ClassProvider<T> getClassProvider(final Reflection reflection, final String className, final boolean forceAccess) throws ClassNotFoundException;

    public <T> ConstructorProvider<T> getConstructorProvider(final Reflection reflection, final Constructor<T> constructor);

    public <T> ConstructorProvider<T> getConstructorProvider(final Reflection reflection, final Class<T> clazz, final Class... args);

    public <T> FieldProvider<T> getFieldProvider(final Reflection reflection, final Field field);

    public <T> FieldProvider<T> getFieldProvider(final Reflection reflection, final Class<?> clazz, final String fieldName);

    public <T> MethodProvider<T> getMethodProvider(final Reflection reflection, final Method method);

    public <T> MethodProvider<T> getMethodProvider(final Reflection reflection, final Class<?> clazz, final String methodName, final Class... args);

    public <T extends Enum<?>> EnumModifier<T> createNewEnumModifier(final Reflection reflection, final Class<T> enumClass);

    public Class<?> loadClass(final String className);
}
