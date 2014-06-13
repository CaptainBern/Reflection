package com.captainbern.minecraft.protocol;

public enum Protocol {

    HANDSHAKING,
    PLAY,
    STATUS,
    LOGIN;

    public static Protocol fromVanilla(Enum<?> enumValue) {
        String name = enumValue.name();

        if ("HANDSHAKING".equals(name)) {
            return HANDSHAKING;
        }
        if ("PLAY".equals(name)) {
            return PLAY;
        }
        if ("STATUS".equals(name)) {
            return STATUS;
        }
        if ("LOGIN".equals(name)) {
            return LOGIN;
        }

        return null;
    }
}
