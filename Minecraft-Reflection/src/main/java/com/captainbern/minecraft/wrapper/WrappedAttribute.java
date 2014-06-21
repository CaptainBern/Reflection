package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.reflection.MinecraftReflection;

public class WrappedAttribute extends AbstractWrapper {

    public WrappedAttribute(Class<?> type) {
        super(MinecraftReflection.getAttributeSnapshotClass());
    }
}
