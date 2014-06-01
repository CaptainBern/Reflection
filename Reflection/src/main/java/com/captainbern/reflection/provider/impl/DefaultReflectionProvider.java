package com.captainbern.reflection.provider.impl;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.provider.ReflectionProvider;
import com.captainbern.reflection.provider.type.ClassProvider;
import com.captainbern.reflection.provider.type.ConstructorProvider;
import com.captainbern.reflection.provider.type.FieldProvider;
import com.captainbern.reflection.provider.type.MethodProvider;
import com.captainbern.reflection.provider.type.impl.DefaultClassProvider;
import com.captainbern.reflection.provider.type.impl.DefaultConstructorProvider;
import com.captainbern.reflection.provider.type.impl.DefaultFieldProvider;
import com.captainbern.reflection.provider.type.impl.DefaultMethodProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DefaultReflectionProvider implements ReflectionProvider {

    @Override
    public <T> ClassProvider<T> getClassProvider(final Reflection reflection, Class<T> clazz, boolean forceAccess) {
        return new DefaultClassProvider<T>(reflection, clazz, forceAccess);
    }

    @Override
    public <T> ClassProvider<T> getClassProvider(final Reflection reflection, String className, boolean forceAccess) throws ClassNotFoundException {
        return new DefaultClassProvider<T>(reflection, (Class<T>) loadClass(className), forceAccess);
    }

    @Override
    public <T> ConstructorProvider<T> getConstructorProvider(final Reflection reflection, Constructor<T> constructor) {
        return new DefaultConstructorProvider<T>(reflection, constructor);
    }

    @Override
    public <T> ConstructorProvider<T> getConstructorProvider(final Reflection reflection, Class<T> clazz, Class... args) {
        try {
            return new DefaultConstructorProvider<T>(reflection, clazz.getDeclaredConstructor(args));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> FieldProvider<T> getFieldProvider(final Reflection reflection, Field field) {
        return new DefaultFieldProvider<T>(reflection, field);
    }

    @Override
    public <T> FieldProvider<T> getFieldProvider(final Reflection reflection, Class<?> clazz, String fieldName) {
        try {
            return new DefaultFieldProvider<T>(reflection, clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> MethodProvider<T> getMethodProvider(final Reflection reflection, Method method) {
        return new DefaultMethodProvider<T>(reflection, method);
    }

    @Override
    public <T> MethodProvider<T> getMethodProvider(final Reflection reflection, Class<?> clazz, String methodName, Class... args) {
        try {
            return new DefaultMethodProvider<T>(reflection, clazz.getDeclaredMethod(methodName, args));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class<?> loadClass(final String className) {
        try {
            return getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find class: " + className);
        }
    }
}
