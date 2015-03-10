package com.captainbern.reflection.asm.accessor;

public interface FieldAccessor extends Accessor {

    public void set(Object instance, int index, Object value) throws IllegalAccessException;

    public Object get(Object instance, int index) throws IllegalAccessException;
}
