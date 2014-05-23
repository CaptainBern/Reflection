package com.captainbern.minecraft.reflection;

import java.util.regex.Pattern;

public class MinecraftReflection {

    /**
     * The Minecraft package
     */
    private static String MINECARFT_PACKAGE;

    private static String MINECARFT_PACKAGE_PREFIX = "net.minecraft.server";

    private static String FORGE_ENTITY_PACKAGE = "net.minecraft.entity";

    /**
     * The Craftbukkit package
     */
    private static String CRAFTBUKKIT_PACKAGE;

    /**
     * The Version tag
     */
    private static String VERSION_TAG = "";

    /**
     * Pattern
     */
    private static final Pattern PACKAGE_VERSION_MATCHER = Pattern.compile(".*\\.(v\\d+_\\d+_\\w*\\d+)");

    private MinecraftReflection() {
        super();
    }

    private void initializePackages() {

    }
}
