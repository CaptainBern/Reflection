package com.captainbern.reflection.provider;

import com.captainbern.reflection.SafeMethod;

import java.lang.reflect.Method;

public interface IMethodProvider {

    public <T>SafeMethod<T> reflect(Method method);
}
