package com.captainbern.minecraft.reflection.utils;

public class ClassUtils {

    private ClassUtils() {}

    public static final String getPackage(final Class<?> clazz) {
        String fullName = clazz.getCanonicalName();
        int index = fullName.lastIndexOf(".");

        if (index > 0)
            return fullName.substring(0, index);
        else
            return ""; // Default package
    }
}
