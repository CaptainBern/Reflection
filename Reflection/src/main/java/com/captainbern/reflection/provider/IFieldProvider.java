package com.captainbern.reflection.provider;

import com.captainbern.reflection.SafeField;

import java.lang.reflect.Field;

public interface IFieldProvider {

    public <T>SafeField<T> reflect(Field field);
}
