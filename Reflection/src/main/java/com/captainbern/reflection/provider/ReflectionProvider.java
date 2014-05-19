package com.captainbern.reflection.provider;

public class ReflectionProvider {

    private static Configuration configuration = null;

    static {
        configuration = new Configuration.Builder()
                .withClassLoader(Thread.currentThread().getContextClassLoader())
                .build();
    }
}
