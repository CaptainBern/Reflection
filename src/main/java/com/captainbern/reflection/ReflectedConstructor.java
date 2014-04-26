package com.captainbern.reflection;

import com.captainbern.reflection.accessor.ConstructorAccessor;

import java.lang.reflect.Constructor;

public interface ReflectedConstructor extends ReflectedMember {

    public Constructor member();

    public ConstructorAccessor getAccessor();
}
