package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.conversion.BukkitConverters;
import com.captainbern.minecraft.conversion.BukkitUnwrapper;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.reflection.utils.Accessor;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class NbtFactory {

    protected static MethodAccessor CREATE_TAG;
    protected static Accessor<NbtTagBase<?>> ITEMSTACK_ACCESSOR;

    /**
     * This method will cast a given NbtTagBase to NbtTagCompound when a valid parameter is passed in.
     * @param nbtTagBase
     * @return
     */
    public static NbtTagCompound toCompound(NbtTagBase<?> nbtTagBase) {
        if (nbtTagBase instanceof NbtTagCompound) {
            return (NbtTagCompound) nbtTagBase;
        } else if (nbtTagBase == null) {
            throw new IllegalArgumentException("Tag can't be NULL!");
        } else {
            throw new RuntimeException("Failed to cast: " + nbtTagBase.getClass() + " to TAG_COMPOUND!");
        }
    }

    /**
     * This method will cast the given NbtTagBase to NbtTagList in case the given parameter is valid.
     * @param nbtTagBase
     * @return
     */
    public static NbtTagList<?> toList(NbtTagBase<?> nbtTagBase) {
        if (nbtTagBase instanceof  NbtTagList) {
            return (NbtTagList<?>) nbtTagBase;
        } else if (nbtTagBase == null) {
            throw new IllegalArgumentException("Tag can't be NULL!");
        } else {
            throw new RuntimeException("Failed to cast: " + nbtTagBase.getClass() + " to TAG_LIST!");
        }
    }

    /**
     * Constructs a new WrappedNbtTag of the given type.
     * @param type
     * @param <T>
     * @return
     */
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

    /**
     * Internal method to create a WrappedNbtTag with a specific value.
     * @param type
     * @param value
     * @param <T>
     * @return
     */
    private static <T> WrappedNbtTag<T> createTagWithValue(NbtType type, T value) {
        WrappedNbtTag<T> tag = createTag(type);
        tag.setValue(value);

        return tag;
    }

    /**
     * Creates a WrappedNbtTag out of an NbtTagBase object.
     * @param nbtTagBase
     * @param <T>
     * @return
     */
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

    /**
     * Constructs a WrappedNbtTag from the given NMS-Object
     * @param nmsHandle
     * @param <T>
     * @return
     */
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

    /**
     * Returns a valid WrappedNbtTag for the given Object, when no valid type is found,
     * and exception will be thrown.
     * @param value
     * @return
     */
    public static WrappedNbtTag<?> forObject(Object value) {
        if (value instanceof WrappedNbtTag)
            return (WrappedNbtTag<?>) value;

        NbtType type = NbtType.getTypeForClass(value.getClass());

        if (type == null)
            throw new IllegalArgumentException("Can't create a WrappedNbtTag for Object: " + value);

        return createTagWithValue(type, value);
    }

    /**
     * Creates a WrappedNbtTag for the given byte.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Byte> forObject(byte value) {
        return createTagWithValue(NbtType.TAG_BYTE, value);
    }

    /**
     * Creates a WrappedNbtTag for the given short.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Short> forObject(short value) {
        return createTagWithValue(NbtType.TAG_SHORT, value);
    }

    /**
     * Creates a WrappedNbtTag for the given integer.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Integer> forObject(int value) {
        return createTagWithValue(NbtType.TAG_INT, value);
    }

    /**
     * Creates a WrappedNbtTag for the given long.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Long> forObject(long value) {
        return createTagWithValue(NbtType.TAG_LONG, value);
    }

    /**
     * Creates a WrappedNbtTag for the given float.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Float> forObject(float value) {
        return createTagWithValue(NbtType.TAG_FLOAT, value);
    }

    /**
     * Creates a WrappedNbtTag for the given double.
     * @param value
     * @return
     */
    public static WrappedNbtTag<Double> forObject(double value) {
        return createTagWithValue(NbtType.TAG_DOUBLE, value);
    }

    /**
     * Creates a WrappedNbtTag for the given byte-array.
     * @param value
     * @return
     */
    public static WrappedNbtTag<byte[]> forObject(byte[] value) {
        return createTagWithValue(NbtType.TAG_BYTE_ARRAY, value);
    }

    /**
     * Creates a WrappedNbtTag for the given string.
     * @param value
     * @return
     */
    public static WrappedNbtTag<String> forObject(String value) {
        return createTagWithValue(NbtType.TAG_STRING, value);
    }

    /**
     * Creates a WrappedNbtTag for the given integer-array.
     * @param value
     * @return
     */
    public static WrappedNbtTag<int[]> forObject(int[] value) {
        return createTagWithValue(NbtType.TAG_INT_ARRAY, value);
    }

    public static NbtTagCompound readFromItemStack(ItemStack itemStack) {
        Accessor<NbtTagBase<?>> accessor = getItemStackAccessor(itemStack);

        NbtTagBase<?> tag = accessor.read(0);

        if (tag == null) {
            tag = createTag(NbtType.TAG_COMPOUND);
            accessor.write(0, tag);
        }

        return toCompound(fromNbtBase(tag));
    }

    public static void writeToItemStack(ItemStack itemStack, NbtTagCompound compound) {
        getItemStackAccessor(itemStack).write(0, compound);
    }

    public static void saveItemStack(NbtTagCompound tagCompound) {

    }

    public static ItemStack loadItemStack(NbtTagCompound tagCompound) {
        return null;
    }

    private static void verifyItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("Cannot read the NBT-data of a NULL-itemstack!");
        } else if (!MinecraftReflection.getCraftItemStackClass().isAssignableFrom(itemStack.getClass())) {
            throw new IllegalArgumentException("Invalid ItemStack: " + itemStack + "!");
        } else if (itemStack.getType().equals(Material.AIR)) {
            throw new IllegalArgumentException("Cannot read the NBT-data of Air!");
        }
    }

    private static Accessor<NbtTagBase<?>> getItemStackAccessor(ItemStack itemStack) {
        verifyItemStack(itemStack);

        Object nmsStack = BukkitUnwrapper.getInstance().unwrap(itemStack);

        if (ITEMSTACK_ACCESSOR == null) {
            ITEMSTACK_ACCESSOR = new Accessor<>(nmsStack, new Reflection().reflect(MinecraftReflection.getItemStackClass()));
        }

        return ITEMSTACK_ACCESSOR
                .withHandle(nmsStack)
                .withType(MinecraftReflection.getNbtBaseClass(), BukkitConverters.getNbtConverter());
    }
}
