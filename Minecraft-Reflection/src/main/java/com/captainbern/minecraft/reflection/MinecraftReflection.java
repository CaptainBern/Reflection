package com.captainbern.minecraft.reflection;

import com.captainbern.minecraft.reflection.utils.ClassUtils;
import com.captainbern.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftReflection {

    private static Reflection MINECRAFT_REFLECTION;
    private static Reflection CRAFTBUKKIT_REFLECTION;

    /**
     * The Minecraft package
     */
    private static String MINECRAFT_PACKAGE;

    private static String MINECRAFT_PACKAGE_PREFIX = "net.minecraft.server";

    private static String FORGE_ENTITY_PACKAGE = "net.minecraft.entity";

    /**
     * The Craftbukkit package
     */
    private static String CRAFTBUKKIT_PACKAGE;

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

    private void initializePackages() {
        Server craftServer = Bukkit.getServer();

        if (craftServer != null) {
            Class<?> craftServerClass = craftServer.getClass();
            CRAFTBUKKIT_PACKAGE = ClassUtils.getPackage(craftServerClass);

            Matcher versionMatcher = PACKAGE_VERSION_MATCHER.matcher(CRAFTBUKKIT_PACKAGE);
            if (versionMatcher.matches()) {

                VERSION_TAG = versionMatcher.group(1);

            } else {

            }
        }
    }
}
