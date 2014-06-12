package com.captainbern.minecraft.wrapper.nbt;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface NbtTagCompound extends NbtTagBase<Map<String, NbtTagBase<?>>>, Iterable<NbtTagBase<?>> {

    public abstract boolean containsKey(String key);

    public abstract Set<String> getKeys();

    public abstract <T> NbtTagBase<T> getValue(String key);

    public abstract <T> NbtTagCompound put(@Nonnull String key, @Nonnull NbtTagBase<T> value);

    public abstract byte getByte(String key);

    public abstract NbtTagCompound setByte(String key, byte value);

    public abstract short getShort(String key);

    public abstract NbtTagCompound setShort(String key, short value);

    public abstract int getInt(String key);

    public abstract NbtTagCompound setInt(String key, int value);

    public abstract long getLong(String key);

    public abstract NbtTagCompound setLong(String key, long value);

    public abstract float getFloat(String key);

    public abstract NbtTagCompound setFloat(String key, float value);

    public abstract double getDouble(String key, double value);

    public abstract NbtTagCompound setDouble(String key, double value);

    public abstract byte[] getByteArray(String key);

    public abstract NbtTagCompound setByteArray(String key, byte[] value);

    public abstract String getString(String key);

    public abstract NbtTagCompound setString(String key, String value);

    public abstract <T> NbtTagList<T> getList(String key);

    public abstract <T> NbtTagCompound setList(String key, NbtTagList<T> value);

    public abstract NbtTagCompound getTagCompound(String key);

    public abstract NbtTagCompound setTagCompound(String key, NbtTagCompound value);

    public abstract int[] getIntArray(String key);

    public abstract NbtTagCompound setIntArray(String key, int[] value);

    public abstract <T> NbtTagBase<?> remove(String key);

    public abstract Iterator<NbtTagBase<?>> iterator();

}
