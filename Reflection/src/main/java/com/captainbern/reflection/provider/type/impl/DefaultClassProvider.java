package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.impl.ClassTemplateImpl;
import com.captainbern.reflection.provider.type.ClassProvider;

@SuppressWarnings("unchecked")
public class DefaultClassProvider<T> implements ClassProvider<T> {

    private final Reflection reflection;
    private final Class clazz;
    private final boolean forceAccess;

    public DefaultClassProvider(final Reflection reflection, final Class<T> clazz, final boolean forceAccess) {
        this.reflection = reflection;
        this.clazz = clazz;
        this.forceAccess = forceAccess;
    }

    @Override
    public Reflection getReflection() {
        return this.reflection;
    }

    @Override
    public ClassTemplate<T> asClassTemplate() {
        return new ClassTemplateImpl<T>(this.reflection, this.clazz, forceAccess);
    }

    @Override
    public Class<T> reflectedClass() {
        return this.clazz;
    }
}
