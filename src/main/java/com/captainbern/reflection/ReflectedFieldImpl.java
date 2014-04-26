package com.captainbern.reflection;

import com.captainbern.reflection.accessor.FieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectedFieldImpl<T> implements ReflectedField<T> {

    protected Field field;

    public ReflectedFieldImpl(Field field) {
        if(field == null)
            throw new IllegalArgumentException("Field can't be NULL!");

        this.field = field;
        try {
            this.field.setAccessible(true);
        } catch (SecurityException e) {
            // Ignore
        }
    }

    @Override
    public Field member() {
        return this.field;
    }

    @Override
    public FieldAccessor<T> getAccessor() {
        return new FieldAccessor<T>() {
            @Override
            public T get(Object instance) {
                try {
                    return (T) field.get(instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void set(Object instance, T value) {
                try {
                    if(!Modifier.isStatic(getModifiers()) && value == null)
                        throw new IllegalArgumentException("Non-static fields require a valid instance passed-in!");

                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void transfer(Object from, Object to) {
                if(field == null)
                    throw new IllegalStateException("Field is NULL!");

                T old = get(to);
                set(to, get(from));
            }

            @Override
            public ReflectedField getField() {
                return ReflectedFieldImpl.this;
            }
        };
    }

    @Override
    public String name() {
        return this.field.getName();
    }

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ReflectedClass> getArguments() {
        return new ArrayList<ReflectedClass>();
    }

    @Override
    public ReflectedClass getType() {
        return Reflection.reflect(this.field.getType());
    }

    @Override
    public String getDescriptor() {
        return Descriptor.getClassDescriptor(getType().getReflectedClass());
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }
}
