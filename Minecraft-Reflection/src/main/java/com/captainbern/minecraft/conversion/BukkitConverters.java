package com.captainbern.minecraft.conversion;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.wrapper.WrappedDataWatcher;
import com.captainbern.minecraft.wrapper.nbt.NbtFactory;
import com.captainbern.minecraft.wrapper.nbt.NbtTagBase;

public class BukkitConverters {

    private static abstract class IgnoreNullConverter<T> implements Converter<T> {

        public abstract T getWrappedValue(Object object);

        @Override
        public final T getWrapped(Object object) {
            if (object == null) {
                return null;
            } else {
                return getWrappedValue(object);
            }
        }

        public abstract Object getUnWrappedValue(Class<?> type, T wrapperType);

        @Override
        public final Object getUnWrapped(Class<?> type, T wrapperType) {
            if (wrapperType == null) {
                return null;
            } else {
                return getUnWrappedValue(type, wrapperType);
            }
        }
    }

    public static Converter<NbtTagBase<?>> getNbtConverter() {
        return new IgnoreNullConverter<NbtTagBase<?>>() {
            @Override
            public NbtTagBase<?> getWrappedValue(Object object) {
                return NbtFactory.fromNmsHandle(object);
            }

            @Override
            public Object getUnWrappedValue(Class<?> type, NbtTagBase<?> wrapperType) {
                return NbtFactory.fromNbtBase(wrapperType).getHandle();
            }
        };
    }

    public static Converter<WrappedDataWatcher> getDataWatcherConverter() {
        return new IgnoreNullConverter<WrappedDataWatcher>() {
            @Override
            public WrappedDataWatcher getWrappedValue(Object object) {
                if (object != null && MinecraftReflection.getDataWatcherClass().isAssignableFrom(object.getClass())) {
                    return new WrappedDataWatcher(object);
                } else if (object instanceof WrappedDataWatcher) {
                    return (WrappedDataWatcher) object;
                } else {
                    throw new IllegalArgumentException("Invalid type: " + object);
                }
            }

            @Override
            public Object getUnWrappedValue(Class<?> type, WrappedDataWatcher wrapperType) {
                return wrapperType.getHandle();
            }
        };
    }

}
