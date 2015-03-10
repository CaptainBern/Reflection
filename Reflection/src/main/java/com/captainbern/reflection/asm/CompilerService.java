package com.captainbern.reflection.asm;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.WeakHashMap;

public class CompilerService {

    private static final Map<ClassLoader, WeakReference<CompilerService>> COMPILER_SERVICE_CACHE = new WeakHashMap<>();

    private static volatile Method defineMethod;

    private ClassLoader classLoader;

    private CompilerService(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.initialize();
        this.cache();
    }

    private void initialize() {
        if (defineMethod != null)
            return;

        try {
            defineMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class, ProtectionDomain.class});
            defineMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void cache() {
        COMPILER_SERVICE_CACHE.put(this.classLoader, new WeakReference<>(this));
    }

    public Class<?> defineClass(String className, byte[] data) {
        this.initialize(); // sanity check

        try {
            return (Class<?>) defineMethod.invoke(this.classLoader, className, data, 0, data.length, this.getClass().getProtectionDomain());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to define the class!", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("An exception occurred while loading the class", e);
        }
    }

    public static CompilerService create(ClassLoader classLoader) {
        if (COMPILER_SERVICE_CACHE.containsKey(classLoader))
            return COMPILER_SERVICE_CACHE.get(classLoader).get();

        return new CompilerService(classLoader);
    }
}
