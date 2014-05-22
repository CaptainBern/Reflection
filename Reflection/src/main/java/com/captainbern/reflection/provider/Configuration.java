package com.captainbern.reflection.provider;

public class Configuration {

    public static class Builder {

        private ClassLoader classLoader;
        private IClassProvider classProvider;
        private IFieldProvider fieldProvider;
        private IConstructorProvider constructorProvider;
        private IMethodProvider methodProvider;

        public Builder withClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder withClassProvider(final IClassProvider classProvider) {
            this.classProvider = classProvider;
            return this;
        }

        public Builder withFieldProvider(final IFieldProvider fieldProvider) {
            this.fieldProvider = fieldProvider;
            return this;
        }

        public Builder withConstructorProvider(final IConstructorProvider constructorProvider) {
            this.constructorProvider = constructorProvider;
            return this;
        }

        public Builder withMethodProvider(final IMethodProvider methodProvider) {
            this.methodProvider = methodProvider;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    protected ClassLoader classLoader;
    protected IClassProvider classProvider;
    protected IFieldProvider fieldProvider;
    protected IConstructorProvider constructorProvider;
    protected IMethodProvider methodProvider;

    protected Configuration(final Builder builder) {
        this.classLoader = builder.classLoader;
        this.classProvider = builder.classProvider;
        this.fieldProvider = builder.fieldProvider;
        this.constructorProvider = builder.constructorProvider;
        this.methodProvider = builder.methodProvider;
    }

    public ClassLoader getClassLoader() {
        if (this.classLoader == null)
            throw new RuntimeException("ClassLoader not defined!");
        return classLoader;
    }

    public IClassProvider getClassProvider() {
        if (this.classProvider == null)
            throw new RuntimeException("ClassProvider not defined!");
        return this.classProvider;
    }

    public IFieldProvider getFieldProvider() {
        if (this.fieldProvider == null)
            throw new RuntimeException("FieldProvider not defined!");
        return this.fieldProvider;
    }

    public IConstructorProvider getConstructorProvider() {
        if (this.constructorProvider == null)
            throw new RuntimeException("ConstructorProvider not defined!");
        return this.constructorProvider;
    }

    public IMethodProvider getMethodProvider() {
        if (this.methodProvider == null)
            throw new RuntimeException("MethodProvider not defined!");
        return this.methodProvider;
    }
}
