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

import java.util.List;
import java.util.Map;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class NbtFactory {

    // An Internal method used to create WrappedNbtTags
    protected static MethodAccessor CREATE_TAG;

    // An Accessor used to easily access the Nbt-field of an ItemStack, in order to be able to read/write Nbt to it
    protected static Accessor<NbtTagBase<?>> ITEMSTACK_ACCESSOR;

    // This method is used to save an ItemStack to a given NbtTagCompound
    protected static MethodAccessor<Object> ITEMSTACK_SAVE;

    // Used to load an ItemStack from a given NbtTagCompound
    protected static MethodAccessor<Object> ITEMSTACK_LOAD;

    // Better known as the "asBukkitCopy" method inside the CraftItemStack class
    protected static MethodAccessor<ItemStack> TO_BUKKIT_ITEMSTACK;

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

    /**
     * Returns the NBTTagCompound of the given ItemStack
     * (Note: this is *not* the NBT-representation of the itemstack, it's just it's internal
     * data)
     * @param itemStack
     * @return
     */
    public static NbtTagCompound readFromItemStack(ItemStack itemStack) {
        Accessor<NbtTagBase<?>> accessor = getItemStackAccessor(itemStack);

        NbtTagBase<?> tag = accessor.read(0);

        if (tag == null) {
            tag = createTag(NbtType.TAG_COMPOUND);
            accessor.write(0, tag);
        }

        return toCompound(fromNbtBase(tag));
    }

    /**
     * Writes the given NbtTagCompound to the given ItemStack
     * @param itemStack
     * @param compound
     */
    public static void writeToItemStack(ItemStack itemStack, NbtTagCompound compound) {
        getItemStackAccessor(itemStack).write(0, compound);
    }

    /**
     * Saves an ItemStack into the given NbtTagCompound. Same way as Minecraft
     * saves the itemstacks to a file.
     * @param itemStack
     * @param tagCompound
     * @return
     */
    public static NbtTagCompound saveItemStack(ItemStack itemStack, NbtTagCompound tagCompound) {
        verifyItemStack(itemStack);

        WrappedNbtTagCompound wrappedNbtTagCompound = (WrappedNbtTagCompound) fromNbtBase(tagCompound);
        Object nmsHandle = BukkitUnwrapper.getInstance().unwrap(itemStack);

        if (ITEMSTACK_SAVE == null) {
            // The ItemStack class
            Class<?> type = MinecraftReflection.getItemStackClass();

            // Just easy access...
            ClassTemplate template = new Reflection().reflect(type);

            // This *should* give us the NBTTagCompound class
            Class<?> nbtTagCompound = wrappedNbtTagCompound.getHandle().getClass();

            // A list of all the methods that match our requirements, this should only contain 1 method
            List<SafeMethod<Object>> candidates = template.getSafeMethods(withReturnType(nbtTagCompound), withArguments(new Class[] {nbtTagCompound}));

            // Victory, the first method in the list should be the one we're looking for
            if (candidates.size() > 0) {
                ITEMSTACK_SAVE = candidates.get(0).getAccessor();
            } else {
                // Damn, we didn't find it.
                throw new RuntimeException("Failed to find save method!");
            }
        }

        ITEMSTACK_SAVE.invoke(nmsHandle, wrappedNbtTagCompound.getHandle());

        return wrappedNbtTagCompound;
    }

    /**
     * Loads an ItemStack from the given NbtTagCompound and will return a Bukkit ItemStack.
     * @param tagCompound
     * @return
     */
    public static ItemStack loadItemStack(NbtTagCompound tagCompound) {
        WrappedNbtTagCompound wrappedNbtTagCompound = (WrappedNbtTagCompound) fromNbtBase(tagCompound);

        if (ITEMSTACK_LOAD == null) {
            // The NBTTagCompound class
            Class<?> nbtTagCompound = wrappedNbtTagCompound.getHandle().getClass();

            // Used to easy access the net.minecraft.server.ItemStack class
            ClassTemplate template = new Reflection().reflect(MinecraftReflection.getItemStackClass());

            // The possible candidates
            List<SafeMethod<Object>> candidates = template.getSafeMethods(withReturnType(MinecraftReflection.getItemStackClass()), withArguments(new Class[] {nbtTagCompound}));

            if (candidates.size() > 0) {
                // Go with the first one...
                ITEMSTACK_LOAD = candidates.get(0).getAccessor();
            } else {
                throw new RuntimeException("Failed to find load method!");
            }
        }

        if (TO_BUKKIT_ITEMSTACK == null) {
            // Template again, easy access....
            ClassTemplate template = new Reflection().reflect(MinecraftReflection.getCraftItemStackClass());

            // Candidates...
            List<SafeMethod<ItemStack>> candidates = template.getSafeMethods(withReturnType(ItemStack.class), withArguments(new Class[] {MinecraftReflection.getItemStackClass()}));

            // Yay, our method is in the list!
            if (candidates.size() > 0) {
                // I guess we've found it!
                TO_BUKKIT_ITEMSTACK = candidates.get(0).getAccessor();
            } else {
                throw new RuntimeException("Failed to find the asBukkitCopy method!");
            }
        }

        return TO_BUKKIT_ITEMSTACK.invokeStatic(ITEMSTACK_LOAD.invokeStatic(wrappedNbtTagCompound.getHandle()));
    }

    /**
     * Internal method used to verify an ItemStack.
     * This will make sure the given ItemStack has valid Nbt-data.
     * @param itemStack
     */
    private static void verifyItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("Cannot read/write the NBT-data of/to a NULL-itemstack!");
        } else if (!MinecraftReflection.getCraftItemStackClass().isAssignableFrom(itemStack.getClass())) {
            throw new IllegalArgumentException("Invalid ItemStack: " + itemStack + "!");
        } else if (itemStack.getType().equals(Material.AIR)) {
            throw new IllegalArgumentException("Cannot read/write the NBT-data of/to Air!");
        }
    }

    /**
     * Returns an Accessor to easily access the fields of an (NMS) ItemStack. This
     * way we can easily retrieve the NbtTag-field.
     * @param itemStack
     * @return
     */
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
