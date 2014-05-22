package com.captainbern.reflection.provider;

import com.captainbern.reflection.ClassTemplate;

public interface IClassProvider {

    public <T>ClassTemplate<T> reflect(Class<T> clazz);
}
