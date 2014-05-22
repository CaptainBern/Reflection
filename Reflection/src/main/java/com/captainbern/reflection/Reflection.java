package com.captainbern.reflection;

import com.captainbern.reflection.provider.ReflectionProvider;
import com.captainbern.reflection.provider.impl.DefaultReflectionProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    private static ReflectionProvider reflectionProvider;

    static {
        reflectionProvider  = new DefaultReflectionProvider();
    }

    private Reflection() {}

    public static ReflectionProvider getReflectionProvider() {
        return reflectionProvider;
    }

    public static void setReflectionProvider(final ReflectionProvider reflectionProvider) {
        if (reflectionProvider == null) {
            throw new IllegalArgumentException("Cannot set the ReflectionProvider to NUL!");
        }
        Reflection.reflectionProvider = reflectionProvider;
    }

    public static <T> ClassTemplate<T> reflect(final Class<T> clazz, boolean forceAccess) {
        if (clazz == null) {
            throw new IllegalArgumentException("Given class may not be NULL!");
        }
        return getReflectionProvider().getClassProvider(clazz, forceAccess).asClassTemplate();
    }

    public static <T> ClassTemplate<T> reflect(final Class<T> clazz) {
        return reflect(clazz, false);
    }

    public static <T> ClassTemplate<T> reflect(final String className, boolean forceAccess) {
        if (className == null) {
            throw new IllegalArgumentException("Class name may not be NULL!");
        }
        return getReflectionProvider().getClassProvider(className, forceAccess).asClassTemplate();
    }

    public static <T> ClassTemplate<T> reflect(final String className) {
        return reflect(className, false);
    }

    public static <T> SafeConstructor<T> reflect(final Constructor<T> constructor) {
        if (constructor == null) {
            throw new IllegalArgumentException("Constructor may not be NULL!");
        }
        return getReflectionProvider().getConstructorProvider(constructor).asSafeConstructor();
    }

    public static <T> SafeConstructor<T> reflect(final Class<T> clazz, final Class... args) {
        if (clazz == null) {
            throw new IllegalArgumentException("can't retrieve the constructor out of NULL!");
        }
        return getReflectionProvider().getConstructorProvider(clazz, args).asSafeConstructor();
    }

    public static <T> SafeField<T> reflect(final Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field may not be NULL!");
        }
        return getReflectionProvider().getFieldProvider(field).asSafeField();
    }

    public static <T> SafeField<T> reflect(final Class<?> clazz, final String name) {
        if (clazz == null || name == null) {
            throw new IllegalArgumentException("Class and field-name may not be NULL!");
        }
        return getReflectionProvider().getFieldProvider(clazz, name).asSafeField();
    }

    public static <T> SafeMethod<T> reflect(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method may not be NULL!");
        }
        return getReflectionProvider().getMethodProvider(method).asSafeMethod();
    }

    public static <T> SafeMethod<T> reflect(final Class<?> clazz, final String name, final Class... args) {
        if (clazz == null || name == null) {
            throw new IllegalArgumentException("Class and method-name may not be NULL!");
        }
        return getReflectionProvider().getMethodProvider(clazz, name, args).asSafeMethod();
    }

    /**
     * Eww methods
     */
    public static List<ClassTemplate<?>> reflectClasses(final List<Class<?>> classes) {
        List<ClassTemplate<?>> classTemplates = new ArrayList<>();
        for(Class<?> clazz : classes) {
            classTemplates.add(reflect(clazz));
        }
        return classTemplates;
    }

    public static List<SafeField<?>> reflectFields(final List<Field> fields) {
        List<SafeField<?>> safeFields = new ArrayList<>();
        for (Field field : fields) {
            safeFields.add(reflect(field));
        }
        return safeFields;
    }

    public static List<SafeMethod<?>> reflectMethods(final List<Method> methods) {
        List<SafeMethod<?>> safeMethods = new ArrayList<>();
        for (Method method : methods) {
            safeMethods.add(reflect(method));
        }
        return safeMethods;
    }

    public static List<SafeConstructor<?>> reflectConstructors(final List<Constructor> constructors) {
        List<SafeConstructor<?>> safeConstructors = new ArrayList<>();
        for(Constructor constructor : constructors) {
            safeConstructors.add(reflect(constructor));
        }
        return safeConstructors;
    }
}
