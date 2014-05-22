package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.SafeMethod;

import java.lang.reflect.Method;

public interface MethodProvider<T> {

    public <T> SafeMethod<T> asSafeMethod();

    public Method reflectedMethod();
}
