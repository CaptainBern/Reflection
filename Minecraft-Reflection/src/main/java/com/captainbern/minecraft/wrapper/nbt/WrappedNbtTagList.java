package com.captainbern.minecraft.wrapper.nbt;

import java.util.Iterator;
import java.util.List;

public class WrappedNbtTagList<T> implements WrappedNbtTag<List<NbtTagBase<T>>>, Iterable<T>,NbtTagList<T> {

    public WrappedNbtTagList(Object nmsHandle) {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object getHandle() {
        return null;
    }

    @Override
    public NbtType getType() {
        return null;
    }

    @Override
    public List<NbtTagBase<T>> getValue() {
        return null;
    }

    @Override
    public void setValue(List<NbtTagBase<T>> value) {

    }
}
