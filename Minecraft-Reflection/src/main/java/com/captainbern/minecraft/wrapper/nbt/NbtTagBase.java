package com.captainbern.minecraft.wrapper.nbt;

public interface NbtTagBase<T> {

    public abstract NbtType getType();

    public abstract T getValue();

    public abstract void setValue(T value);

    public abstract int hashCode();

    public abstract String toString();
}
