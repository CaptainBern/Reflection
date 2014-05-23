package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;

import java.lang.reflect.Method;

public interface MethodProvider<T> {

    public Reflection getReflection();

    public <T> SafeMethod<T> asSafeMethod();

    public Method reflectedMethod();
}
