package com.captainbern.minecraft.wrapper;

public class AbstractWrapper {

    private final Class<?> type;
    private Object handle;

    public AbstractWrapper(final Class<?> type) {
        if (type == null)
            throw new IllegalArgumentException("Handle type can't be NULL!");
        this.type = type;
    }

    public Class<?> getType() {
        return this.type;
    }

    public void setHandle(final Object handle) {
        if (handle == null)
            throw new IllegalArgumentException("Can't set the handle to NULL!");
        if (type.isAssignableFrom(handle.getClass()))
            throw new IllegalArgumentException("Handle needs to be an instance of: " + this.type.getCanonicalName());

        this.handle = handle;
    }

    public Object getHandle() {
        return this.handle;
    }
}
