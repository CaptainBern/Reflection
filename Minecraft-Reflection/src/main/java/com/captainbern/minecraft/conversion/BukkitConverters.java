package com.captainbern.minecraft.conversion;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.wrapper.WrappedDataWatcher;
import com.captainbern.minecraft.wrapper.WrappedWatchableObject;
import com.captainbern.minecraft.wrapper.nbt.NbtFactory;
import com.captainbern.minecraft.wrapper.nbt.NbtTagBase;
import com.captainbern.reflection.conversion.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        public final Object getUnwrapped(Class<?> type, T wrapperType) {
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

    public static Converter<WrappedWatchableObject> getWatchableObjectConverter() {
        return new IgnoreNullConverter<WrappedWatchableObject>() {
            @Override
            public WrappedWatchableObject getWrappedValue(Object object) {
                if (object != null && MinecraftReflection.getWatchableObjectClass().isAssignableFrom(object.getClass()))
                    return new WrappedWatchableObject(object);
                else if (object instanceof WrappedWatchableObject)
                    return (WrappedWatchableObject) object;
                else
                    throw new IllegalArgumentException("Invalid type " + object.getClass());
            }

            @Override
            public Object getUnWrappedValue(Class<?> type, WrappedWatchableObject wrapperType) {
                return null;
            }
        };
    }

    public static <T> Converter<List<T>> getListConverter(final Class<?> unwrapped, final Converter<T> converter) {
        return new IgnoreNullConverter<List<T>>() {
            @Override
            public List<T> getWrappedValue(Object object) {
                if (object instanceof Collection) {
                    List<T> items = new ArrayList<T>();

                    for (Object item : (Collection<Object>) object) {
                        T result = converter.getWrapped(item);

                        if (item != null)
                            items.add(result);
                    }
                    return items;
                }

                return null;
            }

            @Override
            public Object getUnWrappedValue(Class<?> type, List<T> wrapperType) {
                Collection<Object> newContainer = new ArrayList<>();

                // Convert each object
                for (T position : wrapperType) {
                    Object converted = converter.getUnwrapped(type, position);

                    if (position == null)
                        newContainer.add(null);
                    else if (converted != null)
                        newContainer.add(converted);
                }
                return newContainer;
            }
        };
    }
}
