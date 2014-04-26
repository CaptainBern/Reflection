package com.captainbern.reflection;

import java.util.List;

public class ReflectedClassImpl<T> extends AbstractAccess<T> implements ReflectedClass<T> {

    public ReflectedClassImpl(Class<T> clazz) {
        super(clazz, false);
    }

    @Override
    public String getDescriptor() {
        return Descriptor.getClassDescriptor(this.getReflectedClass());
    }

    @Override
    public ReflectedObject<T> newInstance(Object... args) {
        return null;
    }

    @Override
    public List<ReflectedClass> getSuperClasses() {
        return null;
    }
}
