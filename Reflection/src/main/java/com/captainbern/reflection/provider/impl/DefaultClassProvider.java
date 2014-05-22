package com.captainbern.reflection.provider.impl;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.impl.ClassTemplateImpl;
import com.captainbern.reflection.provider.IClassProvider;

public class DefaultClassProvider implements IClassProvider {
    @Override
    public <T> ClassTemplate<T> reflect(Class<T> clazz) {
        return new ClassTemplateImpl<>(clazz, true);
    }
}
