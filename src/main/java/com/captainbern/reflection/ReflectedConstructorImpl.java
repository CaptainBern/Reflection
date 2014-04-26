package com.captainbern.reflection;

import com.captainbern.reflection.accessor.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.util.List;

public class ReflectedConstructorImpl implements ReflectedConstructor {

    @Override
    public Constructor member() {
        return null;
    }

    @Override
    public ConstructorAccessor getAccessor() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ReflectedClass> getArguments() {
        return null;
    }

    @Override
    public ReflectedClass getType() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    @Override
    public int getModifiers() {
        return 0;
    }
}
