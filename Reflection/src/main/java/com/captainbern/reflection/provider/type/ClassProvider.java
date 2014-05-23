package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;

public interface ClassProvider<T> {

    public Reflection getReflection();

    public <T> ClassTemplate<T> asClassTemplate();

    public Class<T> reflectedClass();
}
