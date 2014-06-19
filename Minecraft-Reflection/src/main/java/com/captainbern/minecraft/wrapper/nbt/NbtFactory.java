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

            return createTagWithName(type);
    }

    private static <T> WrappedNbtTag<T> createTagWithName(NbtType type) {
        Object handle = CREATE_TAG.invokeStatic((byte) type.getId());

        switch (type) {
            case TAG_COMPOUND:
                return (WrappedNbtTag<T>) new WrappedNbtTagCompound(handle);
            case TAG_LIST:
            default:
                return new WrappedNbtElement<>(handle);
        }
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

    public static <T> WrappedNbtTag<T> forObject(Object object) {
        return null;
    }
}
