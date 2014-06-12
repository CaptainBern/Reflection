package com.captainbern.minecraft.wrapper.nbt;

public interface WrappedNbtTag<T> extends NbtTagBase<T> {

    public Object getHandle();

}
