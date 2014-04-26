package com.captainbern.reflection;

import com.captainbern.reflection.accessor.MethodAccessor;

import java.lang.reflect.Method;

public interface ReflectedMethod<T> extends ReflectedMember {

    public Method member();

    public MethodAccessor<T> getAccessor();
}
