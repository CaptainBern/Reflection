package com.captainbern.minecraft.reflection.utils;

import com.captainbern.reflection.Reflection;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class ClassCache {

    private final Map<String, Class<?>> cache = new HashMap<>();
    private final Reflection reflection;

    public ClassCache(final Reflection reflection) {
        this.reflection = reflection;
    }

    public Reflection getReflection() {
        return this.reflection;
    }

    public Class<?> getClass(final String name) {
        try {
            Class<?> result = this.cache.get(Preconditions.checkNotNull(name, "Given class-name can't be NULL!"));

            if (result == null) {
                result = this.reflection.getReflectionProvider().loadClass(name);

                if (result == null)
                    throw new IllegalArgumentException("Class-name: " + name + " returned NULL for provider: " + this.reflection.getReflectionProvider().toString());
            }

            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find a class with name: " + name);
        }
    }
}
