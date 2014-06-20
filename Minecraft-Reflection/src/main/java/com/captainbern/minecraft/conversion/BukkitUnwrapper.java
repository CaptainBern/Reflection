package com.captainbern.minecraft.conversion;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.google.common.primitives.Primitives;

import java.util.HashMap;
import java.util.Map;

public class BukkitUnwrapper implements Unwrapper {

    private static BukkitUnwrapper instance = new BukkitUnwrapper();

    private Map<Class<?>, Unwrapper> converterMap = new HashMap<>();

    public static BukkitUnwrapper getInstance() {
        return instance;
    }

    private BukkitUnwrapper() {}

    @Override
    public Object unwrap(Object bukkitHandle) {
        if (bukkitHandle == null)
            return null;

        Class<?> type = bukkitHandle instanceof Class ? (Class<?>) bukkitHandle : bukkitHandle.getClass();

        try {
            if (Primitives.isWrapperType(type) || type.isPrimitive()) {
                return type;
            }

            Unwrapper unwrapper = converterMap.get(type);

            if (unwrapper == null) {
                final FieldAccessor<Object> accessor = new Reflection().reflect(type).getSafeFieldByName("handle").getAccessor();

                unwrapper = new Unwrapper() {

                    @Override
                    public Object unwrap(Object toConvert) {
                        return accessor.get(toConvert);
                    }
                };

                this.converterMap.put(type, unwrapper);
            }

            return unwrapper.unwrap(bukkitHandle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create/find a converter for: " + type.getCanonicalName(), e);
        }
    }

    protected static Unwrapper getFieldUnwrapper(Class<?> type) {
        return null;
    }
}
