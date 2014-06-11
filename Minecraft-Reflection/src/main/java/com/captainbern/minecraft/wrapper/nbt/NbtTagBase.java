package com.captainbern.minecraft.wrapper.nbt;

public interface NbtTagBase<T> {

    public abstract NbtType getType();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract T getValue();

    public abstract void setValue(T value);
}
