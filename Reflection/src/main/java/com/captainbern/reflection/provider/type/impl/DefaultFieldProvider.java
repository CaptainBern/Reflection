package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.impl.SafeFieldImpl;
import com.captainbern.reflection.provider.type.FieldProvider;

import java.lang.reflect.Field;

public class DefaultFieldProvider<T> implements FieldProvider<T> {

    private final Reflection reflection;
    private final Field field;

    public DefaultFieldProvider(final Reflection reflection, final Field field) {
        this.reflection = reflection;
        this.field = field;
    }

    @Override
    public Reflection getReflection() {
        return this.reflection;
    }

    @Override
    public <T> SafeField<T> asSafeField() {
        return new SafeFieldImpl<T>(this.reflection, this.field);
    }

    @Override
    public Field reflectedField() {
        return this.field;
    }
}
