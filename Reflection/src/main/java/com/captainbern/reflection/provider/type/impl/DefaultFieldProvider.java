package com.captainbern.reflection.provider.type.impl;

import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.impl.SafeFieldImpl;
import com.captainbern.reflection.provider.type.FieldProvider;

import java.lang.reflect.Field;

public class DefaultFieldProvider<T> implements FieldProvider<T> {

    private final Field field;

    public DefaultFieldProvider(final Field field) {
        this.field = field;
    }

    @Override
    public <T> SafeField<T> asSafeField() {
        return new SafeFieldImpl<T>(this.field);
    }

    @Override
    public Field reflectedField() {
        return null;
    }
}
