package com.captainbern.reflection.asm.accessor;

import java.lang.reflect.Field;

public interface FieldAccessor extends Accessor {

    public void set(Object instance, int index, Object value) throws IllegalAccessException;

    public void set(Object instance, String fieldName, Object value) throws IllegalAccessException;

    public Object get(Object instance, int index) throws IllegalAccessException;

    public Object get(Object instance, String fieldName) throws IllegalAccessException;

    public Field[] getFieldTable();
}
