package com.captainbern.reflection.provider.impl;

import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.impl.SafeFieldImpl;
import com.captainbern.reflection.provider.IFieldProvider;

import java.lang.reflect.Field;

public class DefaultFieldProvider implements IFieldProvider {
    @Override
    public <T> SafeField<T> reflect(Field field) {
        return new SafeFieldImpl<>(field);
    }
}
