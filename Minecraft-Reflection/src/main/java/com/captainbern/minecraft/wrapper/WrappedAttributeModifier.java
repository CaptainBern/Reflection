package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.reflection.utils.Accessor;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.ConstructorAccessor;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class WrappedAttributeModifier extends AbstractWrapper {

    private static enum Operation {
        ADD(0),
        MULTIPLY_PERCENTAGE(1),
        MULTIPLY(2);

        private final int operation;

        private Operation(int operation) {
            this.operation = operation;
        }

        public int getId() {
            return this.operation;
        }

        public static Operation fromId(int id) {
            for (Operation operation : Operation.values()) {
                if (operation.getId() == id)
                    return operation;
            }

            throw new IllegalArgumentException("Invalid/unknown operation id: " + id);
        }
    }

    protected static Accessor<Object> ATTRIBUTE_MODIFIER;

    protected static ConstructorAccessor<Object> ATTRIBUTE_ACCESSOR;

    private final UUID uuid;
    private final String name;
    private final double amount;
    private final Operation operation;

    protected WrappedAttributeModifier(final UUID uuid, final String name, final double amount, final Operation operation) {
        super(MinecraftReflection.getAttributeModifierClass());

        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
    }

    protected WrappedAttributeModifier(final Object handle, final UUID uuid, final String name, final double amount, final Operation operation) {
        this(uuid, name, amount, operation);

        setHandle(handle);
        initialize(handle);
    }

    protected WrappedAttributeModifier(final Object handle) {
        super(MinecraftReflection.getAttributeModifierClass());

        setHandle(handle);
        initialize(handle);

        this.uuid = (UUID) ATTRIBUTE_MODIFIER.withType(UUID.class).read(0);
        this.name = (String) ATTRIBUTE_MODIFIER.withType(String.class).read(0);
        this.amount = (double) ATTRIBUTE_MODIFIER.withType(double.class).read(0);
        this.operation = Operation.fromId((Integer) ATTRIBUTE_MODIFIER.withType(int.class).read(0));
    }

    public static WrappedAttributeModifier fromHandle(final Object handle) {
        return new WrappedAttributeModifier(handle);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isSaved() {
        return (boolean) ATTRIBUTE_MODIFIER.withType(boolean.class).read(0);
    }

    public void setSaved(boolean flag) {
        ATTRIBUTE_MODIFIER.withType(boolean.class).write(0, flag);
    }

    protected void initialize(Object handle) {
        if (ATTRIBUTE_MODIFIER == null)
            ATTRIBUTE_MODIFIER = new Accessor<Object>(MinecraftReflection.getAttributeModifierClass());

        ATTRIBUTE_MODIFIER.withHandle(handle);
    }

    @Override
    public int hashCode() {
        return this.uuid == null ? 0 : this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return "{\"uuid\":\"" + this.uuid.toString() + "\"," +
                "\"name\":\"" + this.name + "\"," +
                "\"amount\":\"" + this.amount + "\"," +
                "\"operation\":\"" + this.operation.getId() + "\"}";
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    protected static class Builder {

        private UUID uuid;
        private String name = "<Unknown>";
        private Operation operation = Operation.ADD;
        private double amount;

        public Builder withUUID(final UUID uuid) {
            this.uuid = Preconditions.checkNotNull(uuid);
            return this;
        }

        public Builder withName(final String name) {
            this.name = Preconditions.checkNotNull(name);
            return this;
        }

        public Builder withOperation(final Operation operation) {
            this.operation = Preconditions.checkNotNull(operation);
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public WrappedAttributeModifier build() {
            if (ATTRIBUTE_ACCESSOR == null) {
                ClassTemplate template = new Reflection().reflect(MinecraftReflection.getAttributeModifierClass());

                try {
                    ATTRIBUTE_ACCESSOR = template.getSafeConstructor(UUID.class, String.class, double.class, int.class).getAccessor();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to get the AttributeModifier constructor!");
                }
            }

            try {

                return new WrappedAttributeModifier(ATTRIBUTE_ACCESSOR.invoke(
                        this.uuid,
                        this.name,
                        this.amount,
                        this.operation.getId()));

            } catch (Exception e) {
                throw new RuntimeException("Failed to construct a new AttributeModifier!");
            }
        }
    }
}
