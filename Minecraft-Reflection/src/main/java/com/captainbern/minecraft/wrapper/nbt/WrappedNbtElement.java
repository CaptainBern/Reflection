package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class WrappedNbtElement<T> implements NbtWrapper<T> {

    private static volatile MethodAccessor<Byte> GET_TYPE_ID;

    private Object handle;

    private NbtType type;

    public WrappedNbtElement(Object handle) {
        this.handle = handle;
    }

    public WrappedNbtElement(Object handle, String name) {
        this.handle = handle;
        this.setName(name);
    }

    @Override
    public Object getHandle() {
        return this.handle;
    }

    @Override
    public NbtType getType() {
        if (GET_TYPE_ID == null) {
            ClassTemplate nbtBase = new Reflection().reflect(MinecraftReflection.getNbtBaseClass());

            List<SafeMethod<Byte>> methods = nbtBase.getSafeMethods(withReturnType(byte.class));

            if (methods.size() > 0)
                GET_TYPE_ID = methods.get(0).getAccessor();
        }

        if (this.type == null) {
            try {
                type = NbtType.getTypeForId(GET_TYPE_ID.invoke(this.handle));
            } catch (Exception e) {
               throw new RuntimeException("Failed to retrieve the NbtType of: " + this.handle);
            }
        }

        return type;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public T getValue() {
        Class<T> type = (Class<T>) this.getType().getType();
        return new Reflection().reflect(this.handle.getClass()).getSafeFieldByType(type).getAccessor().get(this.handle);
    }

    @Override
    public void setValue(T value) {
        Class<T> type = (Class<T>) this.getType().getType();
        new Reflection().reflect(this.handle.getClass()).getSafeFieldByType(type).getAccessor().set(this.handle, value);
    }
}
