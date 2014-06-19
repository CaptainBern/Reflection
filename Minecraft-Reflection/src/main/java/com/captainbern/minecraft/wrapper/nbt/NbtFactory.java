package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.util.List;
import java.util.Map;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class NbtFactory {

    private static MethodAccessor CREATE_TAG;

    public static NbtTagCompound toCompound(NbtTagBase<?> nbtTagBase) {
        if (nbtTagBase instanceof NbtTagCompound) {
            return (NbtTagCompound) nbtTagBase;
        } else if (nbtTagBase == null) {
            throw new IllegalArgumentException("Tag can't be NULL!");
        } else {
            throw new RuntimeException("Failed to cast: " + nbtTagBase.getClass() + " to TAG_COMPOUND!");
        }
    }

    public static NbtTagList<?> toList(NbtTagBase<?> nbtTagBase) {
        if (nbtTagBase instanceof  NbtTagList) {
            return (NbtTagList<?>) nbtTagBase;
        } else if (nbtTagBase == null) {
            throw new IllegalArgumentException("Tag can't be NULL!");
        } else {
            throw new RuntimeException("Failed to cast: " + nbtTagBase.getClass() + " to TAG_LIST!");
        }
    }

    public static <T> WrappedNbtTag<T> createTag(NbtType type) {
        if (type == null)
            throw new IllegalArgumentException("Given type cannot be NULL!");
        if (type.equals(NbtType.TAG_END))
            throw new IllegalArgumentException("Cannot create an NbtTag with TAG_END!");

        if (CREATE_TAG == null) {

            ClassTemplate nbtBase = new Reflection().reflect(MinecraftReflection.getNbtBaseClass());
            List<SafeMethod<Object>> methods;

            methods = nbtBase.getSafeMethods(withReturnType(MinecraftReflection.getNbtBaseClass()), withArguments(new Class[]{byte.class}));
            CREATE_TAG = methods.get(0).getAccessor();
        }

        Object handle = CREATE_TAG.invokeStatic((byte) type.getId());

        switch (type) {
            case TAG_COMPOUND:
                return (WrappedNbtTag<T>) new WrappedNbtTagCompound(handle);
            case TAG_LIST:
                return (WrappedNbtTag<T>) new WrappedNbtTagList(handle);
            default:
                return new WrappedNbtElement<>(handle);
        }
    }

    private static <T> WrappedNbtTag<T> createTagWithValue(NbtType type, T value) {
        WrappedNbtTag<T> tag = createTag(type);
        tag.setValue(value);

        return tag;
    }

    public static <T> WrappedNbtTag<T> fromNbtBase(NbtTagBase<T> nbtTagBase) {
        if (nbtTagBase instanceof WrappedNbtTag) {
            return (WrappedNbtTag<T>) nbtTagBase;
        } else {
            if (nbtTagBase.getType().equals(NbtType.TAG_COMPOUND)) {

                WrappedNbtTagCompound compound = (WrappedNbtTagCompound) NbtFactory.<Map<String, NbtTagBase<?>>>createTag(NbtType.TAG_COMPOUND);
                T value = nbtTagBase.getValue();

                compound.setValue((Map<String, NbtTagBase<?>>) value);
                return (WrappedNbtTag<T>) compound;

            } else if (nbtTagBase.getType().equals(NbtType.TAG_LIST)) {

                WrappedNbtTagList<T> list = (WrappedNbtTagList<T>) NbtFactory.<List<NbtTagBase<T>>>createTag(NbtType.TAG_LIST);
                list.setValue((List<NbtTagBase<T>>) nbtTagBase.getValue());

                return (WrappedNbtTag<T>) list;

            } else {

                WrappedNbtTag<T> nbtTag = fromNbtBase(nbtTagBase);
                nbtTag.setValue(nbtTagBase.getValue());

                return nbtTag;
            }
        }
    }

    public static <T> WrappedNbtTag<T> fromNmsHandle(Object nmsHandle) {
        WrappedNbtElement<T> element = new WrappedNbtElement<>(nmsHandle);

        if (element.getType().equals(NbtType.TAG_COMPOUND)) {
            return (WrappedNbtTag<T>) new WrappedNbtTagCompound(nmsHandle);
        } else if (element.getType().equals(NbtType.TAG_LIST)) {
            return (WrappedNbtTag<T>) new WrappedNbtTagList<>(nmsHandle);
        } else {
            return element;
        }
    }

    public static WrappedNbtTag<?> forObject(Object value) {
        if (value instanceof WrappedNbtTag)
            return (WrappedNbtTag<?>) value;

        NbtType type = NbtType.getTypeForClass(value.getClass());

        if (type == null)
            throw new IllegalArgumentException("Can't create a WrappedNbtTag for Object: " + value);

        return createTagWithValue(type, value);
    }

    public static WrappedNbtTag<Byte> forObject(byte value) {
        return createTagWithValue(NbtType.TAG_BYTE, value);
    }

    public static WrappedNbtTag<Short> forObject(short value) {
        return createTagWithValue(NbtType.TAG_SHORT, value);
    }

    public static WrappedNbtTag<Integer> forObject(int value) {
        return createTagWithValue(NbtType.TAG_INT, value);
    }

    public static WrappedNbtTag<Long> forObject(long value) {
        return createTagWithValue(NbtType.TAG_LONG, value);
    }

    public static WrappedNbtTag<Float> forObject(float value) {
        return createTagWithValue(NbtType.TAG_FLOAT, value);
    }

    public static WrappedNbtTag<Double> forObject(double value) {
        return createTagWithValue(NbtType.TAG_DOUBLE, value);
    }

    public static WrappedNbtTag<byte[]> forObject(byte[] value) {
        return createTagWithValue(NbtType.TAG_BYTE_ARRAY, value);
    }

    public static WrappedNbtTag<String> forObject(String value) {
        return createTagWithValue(NbtType.TAG_STRING, value);
    }

    public static WrappedNbtTag<int[]> forObject(int[] value) {
        return createTagWithValue(NbtType.TAG_INT_ARRAY, value);
    }
}
