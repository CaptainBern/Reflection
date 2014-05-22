package com.captainbern.reflection.provider;

import com.captainbern.reflection.provider.impl.DefaultClassProvider;
import com.captainbern.reflection.provider.impl.DefaultFieldProvider;
import com.captainbern.reflection.provider.impl.DefaultIConstructorProvider;
import com.captainbern.reflection.provider.impl.DefaultMethodProvider;

public class ReflectionProvider {

    private static Configuration configuration = null;

    static {
        configuration = new Configuration.Builder()
                .withClassLoader(Thread.currentThread().getContextClassLoader())
                .withClassProvider(new DefaultClassProvider())
                .withFieldProvider(new DefaultFieldProvider())
                .withConstructorProvider(new DefaultIConstructorProvider())
                .withMethodProvider(new DefaultMethodProvider())
                .build();
    }

    public IClassProvider getClassProvider() {
        return configuration.getClassProvider();
    }

    public IFieldProvider getFieldProvider() {
        return configuration.getFieldProvider();
    }

    public IConstructorProvider getConstructorProvider() {
        return configuration.getConstructorProvider();
    }

    public IMethodProvider getMethodProvider() {
        return configuration.getMethodProvider();
    }
}
