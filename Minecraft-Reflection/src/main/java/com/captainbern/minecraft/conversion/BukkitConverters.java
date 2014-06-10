package com.captainbern.minecraft.conversion;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.google.common.primitives.Primitives;

import java.util.HashMap;
import java.util.Map;

public class BukkitConverters implements Converter {

    private static BukkitConverters instance = new BukkitConverters();

    private Map<Class<?>, Converter> converterMap = new HashMap<>();

    public static BukkitConverters getInstance() {
        return instance;
    }

    private BukkitConverters() {}

    @Override
    public Object convert(Object bukkitHandle) {
        if (bukkitHandle == null)
            return null;

        Class<?> type = bukkitHandle instanceof Class ? (Class<?>) bukkitHandle : bukkitHandle.getClass();

        try {
            if (Primitives.isWrapperType(type) || type.isPrimitive()) {
                return type;
            }

            Converter converter = converterMap.get(type);

            if (converter == null) {
                final MethodAccessor<Object> accessor = new Reflection().reflect(type).getSafeMethod("getHandle").getAccessor();

                converter = new Converter() {

                    @Override
                    public Object convert(Object toConvert) {
                        return accessor.invoke(toConvert);
                    }
                };

                this.converterMap.put(type, converter);
            }

            return converter;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create/find a converter for: " + bukkitHandle.getClass().getCanonicalName());
        }
    }
}
