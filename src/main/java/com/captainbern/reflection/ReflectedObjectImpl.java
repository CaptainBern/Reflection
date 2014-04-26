package com.captainbern.reflection;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class ReflectedObjectImpl<T> implements ReflectedObject<T> {

    @Override
    public ReflectedClass<T> asReflectedClass() {
        return null;
    }

    @Override
    public T value() {
        return null;
    }

    @Override
    public <C> C cast(Class<C> type) {
        return null;
    }

    @Override
    public Class<T> getReflectedClass() {
        return null;
    }

    @Override
    public Set<ReflectedField> getFields() {
        return null;
    }

    @Override
    public Set<ReflectedField> getDeclaredFields(Class<?> exemptedSuperClass) {
        return null;
    }

    @Override
    public List<ReflectedField> getFieldsByType(Class<?> type) {
        return null;
    }

    @Override
    public ReflectedField getFieldByNameAndType(String name, Class<?> type) {
        return null;
    }

    @Override
    public ReflectedField getFieldByName(String name) {
        return null;
    }

    @Override
    public Set<ReflectedMethod> getMethods() {
        return null;
    }

    @Override
    public Set<ReflectedMethod> getDeclaredMethods(Class<?> exemptedSuperClass) {
        return null;
    }

    @Override
    public boolean isAssignableFrom(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean isAssignableFromObject(Object object) {
        return false;
    }

    @Override
    public boolean isInstanceOf(Object object) {
        return false;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }
}
