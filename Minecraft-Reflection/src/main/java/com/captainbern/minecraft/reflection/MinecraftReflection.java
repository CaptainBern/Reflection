package com.captainbern.minecraft.reflection;

import com.captainbern.minecraft.reflection.providers.StandardReflectionProvider;
import com.captainbern.minecraft.reflection.providers.remapper.RemappedReflectionProvider;
import com.captainbern.minecraft.reflection.utils.ClassCache;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * To understand how this all works, please refer to the bottom of the file.
 */
public class MinecraftReflection {

    private static ClassCache CRAFTBUKKIT_REFLECTION;
    private static ClassCache MINECRAFT_REFLECTION;

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
        Main.getInstance().getLogger().info("Initializing packages");

        Server craftServer = Bukkit.getServer();

        if (craftServer != null) {

            try {

                Class<?> craftServerClass = craftServer.getClass();
                CRAFTBUKKIT_PACKAGE = getPackage(craftServerClass);

                ReflectionConfiguration craftBukkitConfiguration = new ReflectionConfiguration.Builder()
                        .withClassLoader(MinecraftReflection.class.getClassLoader())
                        .withPackagePrefix(CRAFTBUKKIT_PACKAGE)
                        .build();

                CRAFTBUKKIT_REFLECTION = new ClassCache(new Reflection(new StandardReflectionProvider(craftBukkitConfiguration).init()));

                Matcher versionMatcher = PACKAGE_VERSION_MATCHER.matcher(CRAFTBUKKIT_PACKAGE);
                if (versionMatcher.matches()) {
                    VERSION_TAG = versionMatcher.group(1);
                }

                /**
                 * Initialize the NMS-package stuff
                 */
                Class<?> craftEntity = getCraftEntityClass();
                Method getHandle = craftEntity.getDeclaredMethod("getHandle");
                Class<?> returnType = getHandle.getReturnType();

                MINECRAFT_PACKAGE = getPackage(returnType);

                ReflectionConfiguration minecraftConfiguration;

                ReflectionConfiguration.Builder configBuilder = new ReflectionConfiguration.Builder();
                configBuilder.withClassLoader(MinecraftReflection.class.getClassLoader());

                if (!MINECRAFT_PACKAGE.startsWith(MINECRAFT_PACKAGE_PREFIX)) {
                    // The user is probably running a modded server... (MCPC+)

                    // Hack for MCPC+ 1.7.x and above
                    try {
                        if (VERSION_TAG == null || VERSION_TAG.equals("")) {
                            if (getClass("org.bukkit.plugin.java.PluginClassLoader") != null) {
                                ClassTemplate<?> classLoader = new Reflection().reflect(getClass("org.bukkit.plugin.java.PluginClassLoader"));
                                MethodAccessor<String> getNativeVersion = classLoader.getSafeMethod("getNativeVersion").getAccessor();
                                if (getNativeVersion != null) {
                                    VERSION_TAG = getNativeVersion.invokeStatic();
                                }
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        // The class-loader we're looking for appears to be not present... odd...
                    }

                    configBuilder.withPackagePrefix(MINECRAFT_PACKAGE);
                    minecraftConfiguration = configBuilder.build();

                    MINECRAFT_REFLECTION = new ClassCache(new Reflection(new RemappedReflectionProvider(minecraftConfiguration).init()));

                    if (MINECRAFT_PACKAGE.equals(FORGE_ENTITY_PACKAGE)) {
                         MINECRAFT_PACKAGE = combine(MINECRAFT_PACKAGE_PREFIX, VERSION_TAG);
                    } else {
                        MINECRAFT_PACKAGE_PREFIX = MINECRAFT_PACKAGE;
                    }
                } else {
                    configBuilder.withPackagePrefix(MINECRAFT_PACKAGE);
                    minecraftConfiguration = configBuilder.build();
                    MINECRAFT_REFLECTION = new ClassCache(new Reflection(new StandardReflectionProvider(minecraftConfiguration).init()));
                }

            } catch (SecurityException sex) {
                throw new RuntimeException("SEX violation!");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to find method!", e);
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

    private static String combine(final String part1, final String part2) {
        if (Strings.isNullOrEmpty(part1)) {
            return part2;
        } else if (Strings.isNullOrEmpty(part2)) {
            return part1;
        }

        return part1 + "." + part2;
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

            return CRAFTBUKKIT_REFLECTION.getClass(className);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException("Failed to find (Craftbukkit) class: " + className);
        }
    }

    public static Class<?> getMinecraftClass(final String className) throws ClassNotFoundException {
        try {
            if (MINECRAFT_REFLECTION == null)
                initializePackages();

            return MINECRAFT_REFLECTION.getClass(className);
        } catch (Exception e) {
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

/**
 * No.
 */