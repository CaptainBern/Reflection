package com.captainbern.reflection.asm.accessor;

public enum AccessorType {

    FIELD(FieldAccessor.class),
    CONSTRUCTOR(ConstructorAccessor.class),
    METHOD(MethodAccessor.class);

    private final Class<? extends Accessor> accessorType;

    private AccessorType(Class<? extends Accessor> accessorType) {
        this.accessorType = accessorType;
    }

    public Class<?> getAccessorType() {
        return this.accessorType;
    }
}
