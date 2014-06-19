package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.collection.WrapperMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WrappedNbtTagCompound implements WrappedNbtTag<Map<String, NbtTagBase<?>>>, Iterable<NbtTagBase<?>>, NbtTagCompound {

    private WrappedNbtElement<Map<String, Object>> handle;

    private WrapperMap<String, Object, NbtTagBase<?>> nbtMap;

    public WrappedNbtTagCompound(Object nmsHandle) {
        this.handle = new WrappedNbtElement<>(nmsHandle);
    }

    @Override
    public boolean containsKey(String key) {
        return getValue().containsKey(key);
    }

    @Override
    public Set<String> getKeys() {
        return getValue().keySet();
    }

    @Override
    public <T> NbtTagBase<T> getValue(String key) {
        return (NbtTagBase<T>) getValue().get(key);
    }

    @Override
    public <T> NbtTagCompound putObject(String key, Object value) {
        if (value == null) {
            remove(key);
        } else if (value instanceof NbtTagBase) {
            put(key, (NbtTagBase) value);
        } else {
            NbtTagBase<?> tag = NbtFactory.forObject(value);
            put(key, tag);
        }

        return this;
    }

    @Override
    public <T> NbtTagCompound put(String key, NbtTagBase<?> value) {
        getValue().put(key, value);
        return this;
    }

    @Override
    public byte getByte(String key) {
        return (byte) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, byte value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public short getShort(String key) {
        return (short) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, short value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public int getInt(String key) {
        return (int) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, int value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public long getLong(String key) {
        return (long) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, long value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public float getFloat(String key) {
        return (float) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, float value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public double getDouble(String key, double value) {
        return (double) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, double value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public byte[] getByteArray(String key) {
        return (byte[]) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, byte[] value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }

    @Override
    public String getString(String key) {
        return (String) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, String value) {
        getValue().put(key,NbtFactory.forObject(value));
        return this;
    }

    @Override
    public <T> NbtTagList<T> getList(String key) {
        return (NbtTagList<T>) ((NbtTagBase) getValue(key));
    }

    @Override
    public <T> NbtTagCompound put(String key, NbtTagList<T> value) {
        getValue().put(key, value);
        return this;
    }

    @Override
    public NbtTagCompound getTagCompound(String key) {
        return (NbtTagCompound)((NbtTagBase) getValue(key));
    }

    @Override
    public NbtTagCompound put(String key, NbtTagCompound value) {
        getValue().put(key, value);
        return this;
    }

    @Override
    public int[] getIntArray(String key) {
        return (int[]) getValue(key).getValue();
    }

    @Override
    public NbtTagCompound put(String key, int[] value) {
        getValue().put(key, NbtFactory.forObject(value));
        return this;
    }


    @Override
    public <T> NbtTagBase<?> remove(String key) {
        return getValue().remove(key);
    }

    @Override
    public Iterator<NbtTagBase<?>> iterator() {
        return getValue().values().iterator();
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
        if (this.nbtMap == null) {
            this.nbtMap = new WrapperMap<String, Object, NbtTagBase<?>>(this.handle.getValue()) {
                @Override
                public NbtTagBase<?> toWrapped(Object o) {
                    if (o == null)
                        return null;

                    return NbtFactory.fromNmsHandle(o);
                }

                @Override
                public Object toUnwrapped(NbtTagBase<?> nbtTagBase) {
                    if (nbtTagBase == null)
                        return null;

                    return NbtFactory.fromNbtBase(nbtTagBase);
                }
            };
        }
        return this.nbtMap;
    }

    @Override
    public void setValue(Map<String, NbtTagBase<?>> value) {
        for (Map.Entry<String, NbtTagBase<?>> entry : value.entrySet()) {
            if (entry instanceof NbtTagBase) {
                put(entry.getKey(), entry.getValue());
            } else {
                putObject(entry.getKey(), entry.getValue());
            }
        }
    }
}
