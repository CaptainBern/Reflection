package com.captainbern.minecraft.reflection.providers.minecraft.remapper;

public class RemapperException extends RuntimeException {

    public static enum Reason {
        NOT_FOUND,
        NOT_ENABLED,
        NOT_SUPPORTED
    }

    private final Reason reason;

    public RemapperException(final Reason reason) {
          this.reason = reason;
    }

    public Reason getReason() {
        return this.reason;
    }
}
