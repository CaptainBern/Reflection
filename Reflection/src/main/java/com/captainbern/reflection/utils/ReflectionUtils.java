package com.captainbern.reflection.utils;

import com.captainbern.reflection.utils.assertion.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

    public static final String CLASS_SUFFIX = ".class";

    public static final String PACKAGE_SEPARATOR = ".";

    public static final char INTERNAL_CLASS_SEPARATOR = '$';

    private ReflectionUtils() {}

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
            // Ignore;
        }

        if(classLoader == null) {
            classLoader = ReflectionUtils.class.getClassLoader();

            if(classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }

        return classLoader;
    }

    public static Set<Class<?>> getAllSuperClasses(final Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Class<?>> set = new HashSet<>();
        if(clazz != null && !clazz.equals(Object.class)) {
            set.add(clazz);
            set.addAll(getAllSuperClasses(clazz.getSuperclass()));

            for(Class<?> iface : clazz.getInterfaces()) {
                set.addAll(getAllSuperClasses(iface));
            }
        }
        return set;
    }

    public static Set<Field> getAllFields(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Field> set = new HashSet<>();
        for(Class<?> type : getAllSuperClasses(clazz)) {
            set.addAll(getAllFields(type));
        }
        return null;
    }

    public static Set<Field> getFields(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Field> set = new HashSet<>();
        for(Field field : clazz.isInterface() ? clazz.getFields() : clazz.getDeclaredFields()) {
            set.add(field);
        }
        return set;
    }

    public static Field getField(Class<?> clazz, String fieldName, Class<?> fieldType) {
        Assert.assertNotNull(clazz);
        Assert.assertTrue(fieldName != null || fieldType != null, "Either name or type needs to be specified!");

        Set<Field> fields = getFields(clazz);
        for(Field field : fields) {
            if((fieldName == null || field.getName().equals(fieldName)) && (fieldType == null || field.getType().equals(fieldType))) {
                return field;
            }
        }

        return null;
    }

    public static Set<Method> getAllMethods(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Method> set = new HashSet<>();
        for(Class<?> type : getAllSuperClasses(clazz)) {
            set.addAll(getMethods(type));
        }
        return set;
    }

    public static Set<Method> getMethods(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Method> set = new HashSet<>();
        for(Method method : clazz.isInterface() ? clazz.getMethods() : clazz.getDeclaredMethods()) {
            set.add(method);
        }
        return set;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?> returnType, Class[] parameters) {   // Don't dare to ask for varargs here.
        Assert.assertNotNull(clazz);
        Assert.assertTrue(name != null || returnType != null || parameters != null);

        Set<Method> methods = getMethods(clazz);
        for(Method method : methods) {
            if((name == null || method.getName().equals(name)) && (returnType == null || method.getReturnType().equals(returnType)) && (parameters == null || Arrays.equals(parameters, method.getParameterTypes()))) {
                return method;
            }
        }
        return null;
    }

    public static Set<Constructor<?>> getAllConstructors(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Constructor<?>> set = new HashSet<>();
        for(Class<?> type : getAllSuperClasses(clazz)) {
            set.addAll(getConstructors(type));
        }
        return set;
    }

    public static Set<Constructor<?>> getConstructors(Class<?> clazz) {
        Assert.assertNotNull(clazz);

        Set<Constructor<?>> set = new HashSet<>();
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            set.add(constructor);
        }
        return set;
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class... parameters) {
        Assert.assertNotNull(clazz);

        Set<Constructor<?>> constructors = getConstructors(clazz);
        for(Constructor<?> constructor : constructors) {
            if(parameters == null || Arrays.equals(parameters, constructor.getParameterTypes())) {
                return constructor;
            }
        }
        return null;
    }
}
