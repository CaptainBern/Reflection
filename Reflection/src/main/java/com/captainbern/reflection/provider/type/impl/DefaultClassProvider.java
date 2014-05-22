package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.impl.ClassTemplateImpl;
import com.captainbern.reflection.provider.type.ClassProvider;

public class DefaultClassProvider<T> implements ClassProvider<T> {

    private final Class clazz;
    private final boolean forceAccess;

    public DefaultClassProvider(final Class<T> clazz, final boolean forceAccess) {
        this.clazz = clazz;
        this.forceAccess = forceAccess;
    }

    @Override
    public <T> ClassTemplate<T> asClassTemplate() {
        return new ClassTemplateImpl<T>(this.clazz, forceAccess);
    }

    @Override
    public Class<T> reflectedClass() {
        return this.clazz;
    }
}
