package com.captainbern.minecraft.wrapper;

public class AbstractWrapper {

    /**
     * The Wrapper-type.
     */
    private final Class<?> type;

    /**
     * The Handle.
     */
    private Object handle;

    public AbstractWrapper(final Class<?> type) {
        if (type == null)
            throw new IllegalArgumentException("Handle type can't be NULL!");
        this.type = type;
    }

    /**
     * Returns the type of the wrapper.
     * @return
     */
    public Class<?> getType() {
        return this.type;
    }

    /**
     * Sets the handle.
     * @param handle
     */
    public void setHandle(final Object handle) {
        if (handle == null)
            throw new IllegalArgumentException("Can't set the handle to NULL!");
        if (!type.isAssignableFrom(handle.getClass()))
            throw new IllegalArgumentException("Handle needs to be an instance of: " + this.type.getCanonicalName());

        this.handle = handle;
    }

    /**
     * Returns the handle of this wrapper.
     * @return
     */
    public Object getHandle() {
        return this.handle;
    }
}
