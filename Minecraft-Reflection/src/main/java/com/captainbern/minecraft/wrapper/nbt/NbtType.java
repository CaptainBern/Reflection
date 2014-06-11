package com.captainbern.minecraft.wrapper.nbt;

import com.google.common.primitives.Primitives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NbtType {

    TAG_END(0, Void.class),

    TAG_BYTE(1, byte.class),

    TAG_SHORT(2, short.class),

    TAG_INT(3, int.class),

    TAG_LONG(4, long.class),

    TAG_FLOAT(5, float.class),

    TAG_DOUBLE(6, double.class),

    TAG_BYTE_ARRAY(7, byte[].class),

    TAG_STRING(8, String.class),

    TAG_LIST(9, List.class),

    TAG_COMPOUND(10, Map.class),

    TAG_INT_ARRAY(11, int[].class);

    protected final int id;
    protected final Class<?> type;

    protected static NbtType[] registry;
    protected static Map<Class<?>, NbtType> classToTypeRegistry;

    static {
        registry = new NbtType[values().length];
        classToTypeRegistry = new HashMap<>();

        for (NbtType type : values()) {
            registry[type.getId()] = type;

            classToTypeRegistry.put(type.getType(), type);

            if (type.getType().isPrimitive()) {
                classToTypeRegistry.put(Primitives.wrap(type.getType()), type);
            }
        }
    }

    private NbtType(int id, Class<?> type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public Class<?> getType() {
        return this.type;
    }

    public boolean canStore() {
        return this.equals(TAG_COMPOUND) || this.equals(TAG_LIST);
    }

    public static NbtType getTypeForId(int id) {
        if (id < 0 || id > registry.length)
            throw new IllegalArgumentException("Invalid NBT-Opcode: " + id);

        return registry[id];
    }

    public static NbtType getTypeForClass(Class<?> type) {
        NbtType result = classToTypeRegistry.get(type);

        if (result != null) {
            return result;
        }

        throw new RuntimeException("Failed to find a matching NbtType for class: " + type.getCanonicalName());
    }
}
