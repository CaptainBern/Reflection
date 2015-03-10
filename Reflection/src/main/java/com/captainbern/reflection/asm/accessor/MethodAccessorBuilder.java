package com.captainbern.reflection.asm.accessor;

import org.objectweb.asm.ClassWriter;

public class MethodAccessorBuilder extends AccessorBuilder<MethodAccessor> {

    protected MethodAccessorBuilder(Class<?> target) {
        super(target, AccessorType.METHOD);
    }

    @Override
    public MethodAccessor build(ClassLoader classLoader) {
        injectEmptyConstructor(this.getClassWriter());
        return null;
    }

    protected void injectInvoke(ClassWriter classWriter) {

    }
}
