package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withArguments;

public class NbtFactory {

    private static MethodAccessor<Object> CREATE_TAG;
    private static boolean withName;

    public static <T> NbtWrapper<T> createTag(NbtType type, String name) {
        if (type == null)
            throw new IllegalArgumentException("Given type cannot be NULL!");
        if (type.equals(NbtType.TAG_END))
            throw new IllegalArgumentException("Cannot create an NbtTag with TAG_END!");

        if (CREATE_TAG == null) {

            ClassTemplate nbtBase = new Reflection().reflect(MinecraftReflection.getNbtBaseClass());
            List<SafeMethod<Object>> methods;

            try {
                methods = nbtBase.getSafeMethods(withArguments(new Class[]{byte.class, String.class}));
                CREATE_TAG = methods.get(0).getAccessor();
                withName = true;
            } catch (Exception e) {
                methods = nbtBase.getSafeMethods(withArguments(new Class[]{byte.class}));
                CREATE_TAG = methods.get(0).getAccessor();
                withName = false;
            }
        }

        if (withName) {
            return createTagWithName(type, name);
        } else {
            return createTagSetNameAfter(type, name);
        }
    }

    private static <T> NbtWrapper<T> createTagWithName(NbtType type, String name) {
        Object handle = CREATE_TAG.invoke(null, type.getId(), name);

        switch (type) {
            case TAG_COMPOUND:
            case TAG_LIST:
            default:
                return new WrappedNbtElement<>(handle);
        }
    }

    private static <T> NbtWrapper<T> createTagSetNameAfter(NbtType type, String name) {
        Object handle = CREATE_TAG.invoke(null, type.getId());

        switch (type) {
            case TAG_COMPOUND:
            case TAG_LIST:
            default:
                return new WrappedNbtElement<>(handle, name);
        }
    }
}
