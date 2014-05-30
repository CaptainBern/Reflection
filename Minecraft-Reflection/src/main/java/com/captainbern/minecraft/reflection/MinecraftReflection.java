package com.captainbern.minecraft.reflection;

import com.captainbern.minecraft.reflection.providers.StandardReflectionProvider;
import com.captainbern.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftReflection {

    private static Reflection CRAFTBUKKIT_REFLECTION;
    private static Reflection MINECRAFT_REFLECTION;

    /**
     * The Craftbukkit package
     */
    private static String CRAFTBUKKIT_PACKAGE;

    /**
     * The Minecraft package
     */
    private static String MINECRAFT_PACKAGE;

    private static String MINECRAFT_PACKAGE_PREFIX = "net.minecraft.server";

    private static String FORGE_ENTITY_PACKAGE = "net.minecraft.entity";

    /**
     * The Version tag
     */
    private static String VERSION_TAG;

    /**
     * Pattern
     */
    private static final Pattern PACKAGE_VERSION_MATCHER = Pattern.compile(".*\\.(v\\d+_\\d+_\\w*\\d+)");

    private MinecraftReflection() {
        super();
    }

    public static String getVersionTag() {
        if (VERSION_TAG == null)
            initializePackages();
        return VERSION_TAG;
    }

    protected static void initializePackages() {
        Server craftServer = Bukkit.getServer();

        if (craftServer != null) {

            Class<?> craftServerClass = craftServer.getClass();
            CRAFTBUKKIT_PACKAGE = getPackage(craftServerClass);

            ReflectionConfiguration craftBukkitConfiguration = new ReflectionConfiguration.Builder()
                    .withClassLoader(MinecraftReflection.class.getClassLoader())
                    .withPackagePrefix(CRAFTBUKKIT_PACKAGE)
                    .build();

            CRAFTBUKKIT_REFLECTION = new Reflection(new StandardReflectionProvider(craftBukkitConfiguration).init());

            Matcher versionMatcher = PACKAGE_VERSION_MATCHER.matcher(CRAFTBUKKIT_PACKAGE);
            if (versionMatcher.matches()) {
                VERSION_TAG = versionMatcher.group(1);
            }
        }
    }

    private static String getPackage(final Class<?> clazz) {
        String fullName = clazz.getCanonicalName();
        int index = fullName.lastIndexOf(".");

        if (index > 0)
            return fullName.substring(0, index);
        else
            return ""; // Default package
    }

    public static Class<?> getClass(final String className) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Failed to find class: " + className);
        }
    }

    public static Class<?> getCraftBukkitClass(final String className) throws ClassNotFoundException {
        try {
            if (CRAFTBUKKIT_REFLECTION == null)
                initializePackages();

            return CRAFTBUKKIT_REFLECTION.getReflectionProvider().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("Failed to find (Craftbukkit) class: " + className);
        }
    }

    public static Class<?> getMinecraftClass(final String className) throws ClassNotFoundException {
        try {
            if (MINECRAFT_REFLECTION == null)
                initializePackages();

            return MINECRAFT_REFLECTION.getReflectionProvider().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Failed to find (Minecraft) class: " + className);
        }
    }

    /**
     * Usefull methods
     */

    public static Class<?> getCraftServerClass() {
        try {
            return getCraftBukkitClass("CraftServer");
        } catch (ClassNotFoundException e) {
            // Swallow
            return null;
        }
    }

    public static Class<?> getCraftEntityClass() {
        try {
            return getCraftBukkitClass("entity.CraftEntity");
        } catch (ClassNotFoundException e) {
            // Swallow
            e.printStackTrace();
            return null;
        }
    }
}
