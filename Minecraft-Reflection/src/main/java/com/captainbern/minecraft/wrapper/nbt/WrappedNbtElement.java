package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withArgumentCount;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class WrappedNbtElement<T> implements WrappedNbtTag<T> {

    protected static MethodAccessor<Byte> GET_ID;
    protected static FieldAccessor[] accessors = new FieldAccessor[NbtType.values().length];

    private Object handle;

    private NbtType type;

    public WrappedNbtElement(Object handle) {
        if (!MinecraftReflection.getNbtBaseClass().isAssignableFrom(handle.getClass())) {
            throw new IllegalArgumentException("Cannot create a wrapped nbt element with: " + handle.getClass().getCanonicalName());
        }

        this.handle = handle;
    }

    @Override
    public Object getHandle() {
        return this.handle;
    }

    protected FieldAccessor<T> getAccessor() {
        int index = getType().ordinal();
        FieldAccessor<T> accessor = WrappedNbtElement.accessors[index];

        if (accessor == null) {
            synchronized (this) {
                ClassTemplate nbtTemplate = new Reflection().reflect(this.handle.getClass());

                if (nbtTemplate == null) {
                    throw new IllegalStateException("Nbt template is NULL!");  // Should never happen
                }

                SafeField<T> field = nbtTemplate.getSafeFieldByType(getType().getType());

                if (field == null) {
                    throw new RuntimeException("Failed to retrieve a valid field accessor for: " + this + "@" + this.handle);
                }

                accessor = WrappedNbtElement.accessors[index] = field.getAccessor();
            }
        }

        return accessor;
    }

    @Override
    public NbtType getType() {
        if (GET_ID == null) {
            ClassTemplate nbtBaseTemplate = new Reflection().reflect(MinecraftReflection.getNbtBaseClass());

            List<SafeMethod<Byte>> candidates = nbtBaseTemplate.getSafeMethods(withReturnType(byte.class), withArgumentCount(0));

            try {
                GET_ID = candidates.get(0).getAccessor();
            } catch (Exception e) {
                throw new RuntimeException("Failed to retrieve 'getTypeID' method!");
            }
        }

        if (type == null) {
            try {
                type = NbtType.getTypeForId(GET_ID.invoke(this.handle));
            } catch (Exception e) {
                throw new RuntimeException("Failed to retrieve the NBT-Type of: " + this.handle);
            }
        }

        return this.type;
    }

    @Override
    public T getValue() {
        return getAccessor().get(this.handle);
    }

    @Override
    public void setValue(T value) {
        getAccessor().set(this.handle, value);
    }

    @Override
    public int hashCode() {
        return getType().getId();
    }

    @Override
    public String toString() {
        return this.getType().toString() + ":" + getValue();
    }
}
