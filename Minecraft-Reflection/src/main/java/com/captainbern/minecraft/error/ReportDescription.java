package com.captainbern.minecraft.error;

import java.text.MessageFormat;

public class ReportDescription {

    protected final String format;

    public ReportDescription(final String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public String format(Object[] objects) {
        if (objects == null || objects.length < 1) {
            return toString();
        }

        MessageFormat format = new MessageFormat(this.format);
        return format.format(objects);
    }

    @Override
    public String toString() {
        return this.format;
    }
}
