package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.protocol.PacketType;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.conversion.Converter;

import static com.captainbern.reflection.matcher.Matchers.withType;

public class EnumWrappers {

    public static enum EntityUseAction {
        INTERACT,
        ATTACK;
    }

    private static Class<?> ENTITY_USE_ACTION;

    private static void initialize() {
        if (!MinecraftReflection.isUsingNetty())
            throw new IllegalStateException("Not supported on 1.6 and below!");

        ENTITY_USE_ACTION = get(PacketType.Play.Client.USE_ENTITY.getPacketClass(), 0);
    }

    private static Class<?> get(Class<?> clazz, int fieldIndex) {
        return new Reflection().reflect(clazz).getSafeFields(withType(Enum.class)).get(fieldIndex).member().getType();
    }

    public static Class<?> getEntityUseActionClass() {
        if (ENTITY_USE_ACTION == null)
            initialize();

        return ENTITY_USE_ACTION;
    }

    public static EnumConverter<EntityUseAction> getEntityUseActionConverter() {
        return new EnumConverter<>(EntityUseAction.class);
    }

    public static class EnumConverter<T extends Enum<T>> implements Converter<T> {

        private final Class<T> wrapper;

        public EnumConverter(Class<T> wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public T getWrapped(Object object) {
            return Enum.valueOf(this.wrapper, ((Enum) object).name());
        }

        @Override
        public Object getUnwrapped(Class<?> type, T t) {
            return Enum.valueOf((Class) type, t.name());
        }
    }
}
