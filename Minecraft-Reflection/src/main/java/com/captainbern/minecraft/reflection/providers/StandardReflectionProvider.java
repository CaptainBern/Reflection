package com.captainbern.minecraft.reflection.providers;

import com.captainbern.minecraft.reflection.ReflectionConfiguration;
import com.captainbern.reflection.provider.impl.DefaultReflectionProvider;

public class StandardReflectionProvider extends DefaultReflectionProvider {

    private final ReflectionConfiguration configuration;

    public StandardReflectionProvider(final ReflectionConfiguration configuration) {
        this.configuration = configuration;
    }

    public void init() {
        // With the standard reflection provider we do nothing here
    }

    public ReflectionConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return getConfiguration().getClassLoader().loadClass(getConfiguration().getPackagePrefix() + "." + className);
        } catch (Exception e) {
            throw new ClassNotFoundException("Failed to load class: " + className);
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
