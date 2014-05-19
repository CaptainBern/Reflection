package com.captainbern.reflection.provider;

public class Configuration {

    public static class Builder {

        private ClassLoader classLoader;
        private boolean useClassCache;

        public Builder withClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder withClassCache(final boolean state) {
            this.useClassCache = state;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    protected ClassLoader classLoader;
    protected boolean useClassCache;

    protected Configuration(final Builder builder) {
        this.classLoader = builder.classLoader;
        this.useClassCache = builder.useClassCache;
    }

    public ClassLoader getClassLoader() {
        if (this.classLoader == null)
            throw new RuntimeException("No ClassLoader defined!");
        return classLoader;
    }
}
