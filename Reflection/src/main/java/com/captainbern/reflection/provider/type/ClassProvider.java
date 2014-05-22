package com.captainbern.reflection.provider.type;

import com.captainbern.reflection.ClassTemplate;

public interface ClassProvider<T> {

    public <T> ClassTemplate<T> asClassTemplate();

    public Class<T> reflectedClass();
}
