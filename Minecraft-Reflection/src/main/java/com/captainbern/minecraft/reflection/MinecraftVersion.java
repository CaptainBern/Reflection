package com.captainbern.minecraft.reflection;

import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used as a backup when we failed to retrieve the version of the version package.
 * This is mostly done because MCPC+ removes both the safeguard from the NMS classes and CraftBukkit classes.
 */
public class MinecraftVersion implements Serializable, Comparable<MinecraftVersion> {

    /**
     * Represents the skin-update (aka the day Mojang decided to break NPC's)
     */
    public static final MinecraftVersion SKIN_UPDATE = new MinecraftVersion(1, 7, 8);
    /**
     * Represents the world-update (aka the day Mojang decided minecraft needed canyons)
     */
    public static final MinecraftVersion WORLD_UPDATE = new MinecraftVersion(1, 7, 2);
    /**
     * Represents the horse-update (aka the day Mojang decided to horsify Minecraft)
     */
    public static final MinecraftVersion HORSE_UPDATE = new MinecraftVersion(1, 6, 1);
    /**
     * Represents the redstone-update (aka the day Mojang decided to be cool)
     */
    public static final MinecraftVersion REDSTONE_UPDATE = new MinecraftVersion(1, 5, 0);
    /**
     * Represents the scary-update (aka the day Mojang scared us)
     */
    public static final MinecraftVersion SCARY_UPDATE = new MinecraftVersion(1, 4, 2);
    /**
     * A pattern used to obtain the version tag from the bukkit-version
     */
    private static final Pattern BUKKIT_VERSION_PATTERN = Pattern.compile("([0-9]+)(\\.)([0-9]+)(\\.)([0-9]+)(-)(R)([0-9]+)");
    /**
     * In case the above pattern fails, we will use this.
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile(".*\\(.*MC.\\s*([a-zA-z0-9\\-\\.]+)\\s*\\)");
    private int major;
    private int minor;
    private int build;
    private int release;

    public MinecraftVersion(String versionString) {
        /**
         * Let's try to retrieve the Minecraft version out of the given bukkit version
         */
        Matcher bukkitVersionMatcher = BUKKIT_VERSION_PATTERN.matcher(versionString);
        if (bukkitVersionMatcher.find()) {

            this.major = Integer.parseInt(bukkitVersionMatcher.group(1));
            this.minor = Integer.parseInt(bukkitVersionMatcher.group(3));
            this.build = Integer.parseInt(bukkitVersionMatcher.group(5));
            this.release = Integer.parseInt(bukkitVersionMatcher.group(8));
            return;
        }

        Matcher mcVersionMatcher = VERSION_PATTERN.matcher(versionString);
        if (mcVersionMatcher.matches() && mcVersionMatcher.group(1) != null) {

            String version = mcVersionMatcher.group(1);
            int[] numbers = parseVersion(version);
            this.major = numbers[0];
            this.minor = numbers[1];
            this.build = numbers[1];
            this.release = 1;
            return;
        }
    }

    public MinecraftVersion(int major, int minor, int build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.release = 1;   // The release never will and can be '0'
    }

    public MinecraftVersion(int major, int minor, int build, int release) {
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.release = release;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getBuild() {
        return this.build;
    }

    public int getRelease() {
        return release;
    }

    public String toSafeguardTag() {
        if (SCARY_UPDATE.compareTo(this) <= 0) {
            return "v" + this.major + "_" + this.minor + "_R" + (this.release < 1 ? "1" : release);
        }
        return "";
    }

    private int[] parseVersion(final String version) {
        String[] split = version.split("\\.");
        int[] data = new int[3];

        if (split.length < 1) {
            throw new IllegalArgumentException("Failed to parse a proper Minecraft version for the given input-string: " + version);
        }

        for (int i = 0; i < Math.min(split.length, data.length); i++) {
            data[i] = Integer.parseInt(split[i].trim());
        }

        return data;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s.%s", getMajor(), getMinor(), getBuild(), getRelease());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }

        if (other instanceof MinecraftVersion) {
            MinecraftVersion otherVersion = (MinecraftVersion) other;

            return getMajor() == otherVersion.getMajor() &&
                    getMinor() == otherVersion.getMinor() &&
                    getBuild() == otherVersion.getBuild() &&
                    getRelease() == otherVersion.getRelease();
        }

        return false;
    }

    @Override
    public int compareTo(MinecraftVersion o) {
        if (o == null) {
            return 1;
        }

        return ComparisonChain.start().compare(getMajor(), o.getMajor()).compare(getMinor(), o.getMinor()).compare(getBuild(), o.getBuild()).result();
    }
}
