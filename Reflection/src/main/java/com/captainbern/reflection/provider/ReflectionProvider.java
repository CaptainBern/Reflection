package com.captainbern.reflection.provider;

public class ReflectionProvider {

    private static Configuration configuration = null;

    static {
        configuration = new Configuration.Builder()
                .withClassLoader(Thread.currentThread().getContextClassLoader())
                .build();
    }

    public IClassProvider getClassProvider() {
        return null;
    }

    public IFieldProvider getFieldProvider() {
        return null;
    }

    public IConstructorProvider getConstructorProvider() {
        return null;
    }

    public IMethodProvider getMethodProvider() {
        return null;
    }
}
