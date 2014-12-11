package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.collection.WrapperSet;
import com.captainbern.minecraft.protocol.PacketType;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.reflection.utils.Accessor;
import com.captainbern.reflection.accessor.ConstructorAccessor;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class WrappedAttribute extends AbstractWrapper {

    private static Accessor<Object> ATTRIBUTE_ACCESSOR;
    private static ConstructorAccessor<Object> CONSTRUCTOR_ACCESSOR;

    protected Accessor<Object> modifier;

    protected Set<WrappedAttributeModifier> modifiers;

    private WrappedAttribute(Object nmsHandle) {
        super(MinecraftReflection.getAttributeSnapshotClass());
        setHandle(nmsHandle);

        if (ATTRIBUTE_ACCESSOR == null) {
            ATTRIBUTE_ACCESSOR = new Accessor<Object>(MinecraftReflection.getAttributeSnapshotClass());
        }

        ATTRIBUTE_ACCESSOR.withHandle(nmsHandle);
    }

    public static WrappedAttribute fromHandle(Object nmsHandle) {
        return new WrappedAttribute(nmsHandle);
    }

    public String getKey() {
        return (String) this.modifier.withType(String.class).read(0);
    }

    public double getBaseValue() {
        return (double) this.modifier.withType(double.class).read(0);
    }

    public double getFinalValue() {
        return 0.0;  // TODO: fix
    }

    public WrappedPacket getPacket() {
        return new WrappedPacket(
                PacketType.Play.Server.UPDATE_ATTRIBUTES,
                this.modifier.withType(MinecraftReflection.getPacketClass()).read(0));
    }

    public Set<WrappedAttributeModifier> getModifiers() {
        if (this.modifiers == null) {
            Collection<Object> mods = (Collection<Object>) this.modifier.withType(Collection.class).read(0);

            WrapperSet<Object, WrappedAttributeModifier> wrapperSet = new WrapperSet<Object, WrappedAttributeModifier>(mods) {
                @Override
                public WrappedAttributeModifier toWrapped(Object o) {
                    return WrappedAttributeModifier.fromHandle(o);
                }

                @Override
                public Object toUnwrapped(WrappedAttributeModifier wrappedAttributeModifier) {
                    return wrappedAttributeModifier.getHandle();
                }
            };

            this.modifiers = wrapperSet;
        }

        return this.modifiers;
    }

    public boolean contains(UUID uuid) {
        return this.getModifiers().contains(WrappedAttributeModifier.newBuilder().withUUID(uuid));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    protected static class Builder {

    }
}
