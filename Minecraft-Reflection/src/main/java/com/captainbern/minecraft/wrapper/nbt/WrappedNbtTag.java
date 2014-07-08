package com.captainbern.minecraft.wrapper.nbt;

import java.io.DataOutput;

public interface WrappedNbtTag<T> extends NbtTagBase<T> {

    public Object getHandle();

    public void write(DataOutput dataOutput);

}
