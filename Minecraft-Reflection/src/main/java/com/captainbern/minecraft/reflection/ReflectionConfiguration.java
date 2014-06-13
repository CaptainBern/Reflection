package com.captainbern.minecraft.reflection;

public class ReflectionConfiguration {

    private final String packagePrefix;
    private final ClassLoader classLoader;
    private ReflectionConfiguration(final Builder builder) {
        this.packagePrefix = builder.packagePrefix;
        this.classLoader = builder.classLoader;
    }

    public String getPackagePrefix() {
        if (this.packagePrefix == null) {
            throw new RuntimeException("Package-prefix not set!");
        }

        return this.packagePrefix;
    }

    public ClassLoader getClassLoader() {
        if (this.classLoader == null) {
            throw new RuntimeException("ClassLoader not set!");
        }

        return this.classLoader;
    }

    public static class Builder {

        private String packagePrefix;
        private ClassLoader classLoader;

        public Builder() {
        }

        public Builder withPackagePrefix(final String packagePrefix) {
            this.packagePrefix = packagePrefix;
            return this;
        }

        public Builder withClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public ReflectionConfiguration build() {
            return new ReflectionConfiguration(this);
        }
    }
}
