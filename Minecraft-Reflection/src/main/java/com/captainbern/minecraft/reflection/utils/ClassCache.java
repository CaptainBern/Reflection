package com.captainbern.minecraft.reflection.utils;

import com.captainbern.reflection.provider.ReflectionProvider;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class ClassCache {

    private final Map<String, Class<?>> cache = new HashMap<>();
    private final ReflectionProvider provider;

    public ClassCache(final ReflectionProvider reflectionProvider) {
        this.provider = reflectionProvider;
    }

    public ReflectionProvider getProvider() {
        return this.provider;
    }

    public Class<?> getClass(final String name) {
        try {
            Class<?> result = this.cache.get(Preconditions.checkNotNull(name, "Given class-name can't be NULL!"));

            if (result == null) {
                result = this.provider.loadClass(name);

                if (result == null)
                    throw new IllegalArgumentException("Class-name: " + name + " returned NULL for provider: " + this.provider.toString());
            }

            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find a class with name: " + name);
        }
    }
}
