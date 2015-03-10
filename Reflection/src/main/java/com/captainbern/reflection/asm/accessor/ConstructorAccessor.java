package com.captainbern.reflection.asm.accessor;

public interface ConstructorAccessor extends Accessor {

    public Object newInstance(Object... args);
}
