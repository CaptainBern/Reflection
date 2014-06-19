package com.captainbern.minecraft.wrapper.nbt;

import java.util.Collection;
import java.util.List;

public interface NbtTagList<T> extends NbtTagBase<List<NbtTagBase<T>>>, Iterable<T> {

    public abstract NbtType getListType();

    public abstract void setListType(NbtType nbtType);

    public abstract void add(NbtTagBase<T> nbtTagBase);

    public abstract void add(byte b);

    public abstract void add(short s);

    public abstract void add(int i);

    public abstract void add(long l);

    public abstract void add(float f);

    public abstract void add(double d);

    public abstract void add(byte[] ba);

    public abstract void add(String s);

    public abstract void add(int[] ia);

    public abstract void remove(Object value);

    public abstract T getValue(int index);

    public abstract Collection<NbtTagBase<T>> toCollection();

    public int size();

}
