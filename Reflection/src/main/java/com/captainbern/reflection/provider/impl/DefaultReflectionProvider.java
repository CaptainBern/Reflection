package com.captainbern.reflection.provider.impl;

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
    public <T> ClassProvider<T> getClassProvider(Class<T> clazz, boolean forceAccess) {
        return new DefaultClassProvider<T>(clazz, forceAccess);
    }

    @Override
    public <T> ClassProvider<T> getClassProvider(String className, boolean forceAccess) {
        return new DefaultClassProvider<T>(getClass(className), forceAccess);
    }

    @Override
    public <T> ConstructorProvider<T> getConstructorProvider(Constructor<T> constructor) {
        return new DefaultConstructorProvider<T>(constructor);
    }

    @Override
    public <T> ConstructorProvider<T> getConstructorProvider(Class<T> clazz, Class... args) {
        try {
            return new DefaultConstructorProvider<T>(clazz.getDeclaredConstructor(args));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> FieldProvider<T> getFieldProvider(Field field) {
        return new DefaultFieldProvider<T>(field);
    }

    @Override
    public <T> FieldProvider<T> getFieldProvider(Class<?> clazz, String fieldName) {
        try {
            return new DefaultFieldProvider<T>(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> MethodProvider<T> getMethodProvider(Method method) {
        return new DefaultMethodProvider<T>(method);
    }

    @Override
    public <T> MethodProvider<T> getMethodProvider(Class<?> clazz, String methodName, Class... args) {
        try {
            return new DefaultMethodProvider<T>(clazz.getDeclaredMethod(methodName, args));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
