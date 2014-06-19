package com.captainbern.minecraft.conversion;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.wrapper.WrappedDataWatcher;

public class BukkitConverters {

    public static Converter<WrappedDataWatcher> getDataWatcherConverter() {
        return new Converter<WrappedDataWatcher>() {
            @Override
            public WrappedDataWatcher getWrapped(Object object) {
                if (object != null && MinecraftReflection.getDataWatcherClass().isAssignableFrom(object.getClass())) {
                    return new WrappedDataWatcher(object);
                } else if (object instanceof WrappedDataWatcher) {
                    return (WrappedDataWatcher) object;
                } else {
                    throw new IllegalArgumentException("Invalid type: " + object);
                }
            }

            @Override
            public Object getUnWrapped(Class<?> type, WrappedDataWatcher wrapperType) {
                return wrapperType.getHandle();
            }
        };
    }

}
