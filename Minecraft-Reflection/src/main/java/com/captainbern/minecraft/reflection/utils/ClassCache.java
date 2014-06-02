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

    public void set(String key, Class<?> value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        this.cache.put(key, value);
    }

    public Class<?> getClass(final String name) {
        Class<?> result = this.cache.get(Preconditions.checkNotNull(name, "Given class-name can't be NULL!"));
        if (result == null) {
            result = this.reflection.getReflectionProvider().loadClass(name);

            if (result == null)
                throw new IllegalArgumentException("Class-name: " + name + " returned NULL for provider: " + this.reflection.getReflectionProvider().toString());
            cache.put(name, result);
        }

        return result;
    }
}
