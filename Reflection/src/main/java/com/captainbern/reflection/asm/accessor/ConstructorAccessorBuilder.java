package com.captainbern.reflection.asm.accessor;

import org.objectweb.asm.ClassWriter;

public class ConstructorAccessorBuilder extends AccessorBuilder<ConstructorAccessor> {

    protected ConstructorAccessorBuilder(Class<?> target) {
        super(target, AccessorType.CONSTRUCTOR);
    }

    @Override
    public ConstructorAccessor build(ClassLoader classLoader) {
        injectConstructor(this.getClassWriter());
        return null;
    }

    protected static void injectGetSafeConstructor(ClassWriter classWriter) {

    }

    protected static void injectInvoke(ClassWriter classWriter) {
        
    }
}
