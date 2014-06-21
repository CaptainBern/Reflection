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

            return unwrap(type, bukkitHandle);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create/find a converter for: " + type.getCanonicalName(), e);
        }
    }

    private Object unwrap(Class<?> type, Object handle) {
        try {
            Unwrapper unwrapper = this.converterMap.get(type);

            if (unwrapper == null) {

                try {

                    unwrapper = getMethodUnwrapper(type);
                    unwrapper.unwrap(handle); // Test it
                } catch (Exception e) { // We failed, let's if we get more luck with fields...
                    unwrapper = getFieldUnwrapper(type);
                    unwrapper.unwrap(handle);
                }
            }

            this.converterMap.put(type, unwrapper);

            return unwrapper.unwrap(handle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unwrap: " + handle + "(" + type.getCanonicalName() + ")");
        }
    }

    protected Unwrapper getFieldUnwrapper(Class<?> type) {
        final FieldAccessor<Object> accessor = new Reflection().reflect(type).getSafeFieldByName("handle").getAccessor();

        return new Unwrapper() {
            @Override
            public Object unwrap(Object toConvert) {
                return accessor.get(toConvert);
            }
        };
    }

    protected Unwrapper getMethodUnwrapper(Class<?> type) {
        final MethodAccessor<Object> accessor = new Reflection().reflect(type).getSafeMethod("getHandle").getAccessor();

        return new Unwrapper() {
            @Override
            public Object unwrap(Object toConvert) {
                return accessor.invoke(toConvert);
            }
        };
    }
}
