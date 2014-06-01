package com.captainbern.minecraft.reflection.providers;

import com.captainbern.minecraft.reflection.ReflectionConfiguration;
import com.captainbern.reflection.provider.impl.DefaultReflectionProvider;

public class StandardReflectionProvider extends DefaultReflectionProvider {

    private final ReflectionConfiguration configuration;

    public StandardReflectionProvider(final ReflectionConfiguration configuration) {
        this.configuration = configuration;
    }

    public ReflectionConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public Class<?> loadClass(String className) {
        try {
            className = getConfiguration().getPackagePrefix() + "." + className;
            return getConfiguration().getClassLoader().loadClass(className);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load class: " + className, e);
        }
    }

    @Override
    public String toString() {
        return "SMR-Provider";
    }

    @Override
    public int hashCode() {
        return toString().hashCode() ^ super.hashCode();
    }
}
