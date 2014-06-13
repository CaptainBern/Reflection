package com.captainbern.minecraft.wrapper.nbt;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WrappedNbtTagCompound implements WrappedNbtTag<Map<String, NbtTagBase<?>>>, Iterable<NbtTagBase<?>>, NbtTagCompound {

    private WrappedNbtElement<Map<String, NbtTagBase<?>>> handle;

    public WrappedNbtTagCompound(Object nmsHandle) {
        this.handle = new WrappedNbtElement<>(nmsHandle);
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public Set<String> getKeys() {
        return null;
    }

    @Override
    public <T> NbtTagBase<T> getValue(String key) {
        return null;
    }

    @Override
    public <T> NbtTagCompound put(
            @Nonnull
            String key,
            @Nonnull
            NbtTagBase<T> value) {
        return null;
    }

    @Override
    public byte getByte(String key) {
        return 0;
    }

    @Override
    public NbtTagCompound setByte(String key, byte value) {
        return null;
    }

    @Override
    public short getShort(String key) {
        return 0;
    }

    @Override
    public NbtTagCompound setShort(String key, short value) {
        return null;
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public NbtTagCompound setInt(String key, int value) {
        return null;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public NbtTagCompound setLong(String key, long value) {
        return null;
    }

    @Override
    public float getFloat(String key) {
        return 0;
    }

    @Override
    public NbtTagCompound setFloat(String key, float value) {
        return null;
    }

    @Override
    public double getDouble(String key, double value) {
        return 0;
    }

    @Override
    public NbtTagCompound setDouble(String key, double value) {
        return null;
    }

    @Override
    public byte[] getByteArray(String key) {
        return new byte[0];
    }

    @Override
    public NbtTagCompound setByteArray(String key, byte[] value) {
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public NbtTagCompound setString(String key, String value) {
        return null;
    }

    @Override
    public <T> NbtTagList<T> getList(String key) {
        return null;
    }

    @Override
    public <T> NbtTagCompound setList(String key, NbtTagList<T> value) {
        return null;
    }

    @Override
    public NbtTagCompound getTagCompound(String key) {
        return null;
    }

    @Override
    public NbtTagCompound setTagCompound(String key, NbtTagCompound value) {
        return null;
    }

    @Override
    public int[] getIntArray(String key) {
        return new int[0];
    }

    @Override
    public NbtTagCompound setIntArray(String key, int[] value) {
        return null;
    }

    @Override
    public <T> NbtTagBase<?> remove(String key) {
        return null;
    }

    @Override
    public Iterator<NbtTagBase<?>> iterator() {
        return null;
    }

    @Override
    public Object getHandle() {
        return this.handle.getHandle();
    }

    @Override
    public NbtType getType() {
        return NbtType.TAG_COMPOUND;
    }

    @Override
    public Map<String, NbtTagBase<?>> getValue() {
        return null;
    }

    @Override
    public void setValue(Map<String, NbtTagBase<?>> value) {

    }
}
