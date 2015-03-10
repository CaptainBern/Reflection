package com.captainbern.reflection.asm.accessor;

public interface MethodAccessor extends Accessor {

    public Object invoke(int index, Object... args);
}
