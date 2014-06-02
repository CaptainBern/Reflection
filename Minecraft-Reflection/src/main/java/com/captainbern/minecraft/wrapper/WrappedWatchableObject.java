package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.reflection.MinecraftReflection;

public class WrappedWatchableObject extends AbstractWrapper {

    public WrappedWatchableObject() {
        super(MinecraftReflection.getWatchableObjectClass());
    }
}
