package com.captainbern.reflection;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class ReflectedObjectImpl<T> implements ReflectedObject<T> {

    protected T object;

    public ReflectedObjectImpl(final T object) {
        if(object == null)
            throw new IllegalArgumentException("Object is NULL!");

        this.object = object;
    }

    @Override
    public ReflectedClass<T> asReflectedClass() {
        return (ReflectedClass<T>) Reflection.reflect(this.object);
    }

    @Override
    public T value() {
        return this.object;
    }

    @Override
    public <C> C cast(Class<C> type) {
        return type.cast(this.object);
    }

    @Override
    public Class<T> getReflectedClass() {
        return asReflectedClass().getReflectedClass();
    }

    @Override
    public Set<ReflectedField> getFields() {
        return asReflectedClass().getFields();
    }

    @Override
    public Set<ReflectedField> getDeclaredFields(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredFields(exemptedSuperClass);
    }

    @Override
    public List<ReflectedField> getFieldsByType(Class<?> type) {
        return asReflectedClass().getFieldsByType(type);
    }

    @Override
    public ReflectedField getFieldByNameAndType(String name, Class<?> type) {
        return asReflectedClass().getFieldByNameAndType(name, type);
    }

    @Override
    public ReflectedField getFieldByName(String name) {
        return asReflectedClass().getFieldByName(name);
    }

    @Override
    public Set<ReflectedMethod> getMethods() {
        return asReflectedClass().getMethods();
    }

    @Override
    public Set<ReflectedMethod> getDeclaredMethods(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredMethods(exemptedSuperClass);
    }

    @Override
    public Set<ReflectedConstructor> getConstructors() {
        return asReflectedClass().getConstructors();
    }

    @Override
    public Set<ReflectedConstructor> getDeclaredConstructors(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredConstructors(exemptedSuperClass);
    }

    @Override
    public boolean isAssignableFrom(Class<?> clazz) {
        return asReflectedClass().isAssignableFrom(clazz);
    }

    @Override
    public boolean isAssignableFromObject(Object object) {
        return asReflectedClass().isAssignableFrom(object.getClass());
    }

    @Override
    public boolean isInstanceOf(Object object) {
        return asReflectedClass().isInstanceOf(object);
    }

    @Override
    public Type getType() {
        return asReflectedClass().getType();
    }
}
