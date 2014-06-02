package com.captainbern.minecraft.reflection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used as a backup when we failed to retrieve the version of the version package.
 * This is mostly done because MCPC+ removes both the safeguard from the NMS classes and CraftBukkit classes.
 */
public class MinecraftVersion {

    private static final Pattern BUKKIT_VERSION_MATCHER = Pattern.compile("([0-9]+)(\\.)([0-9]+)(\\.)([0-9]+)(-)(R)([0-9]+)");

    private int major;
    private int minor;
    private int build;
    private int release;

    public MinecraftVersion(String versionString) {
        Matcher versionMatcher = BUKKIT_VERSION_MATCHER.matcher(versionString);
        if (versionMatcher.find()) {
            this.major = Integer.parseInt(versionMatcher.group(1));
            this.minor = Integer.parseInt(versionMatcher.group(3));
            this.build = Integer.parseInt(versionMatcher.group(5)); // We do not need this one
            this.release = Integer.parseInt(versionMatcher.group(8));
        } else {
            throw new IllegalArgumentException("Cannot parse version for given string: " + versionString + "!");
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
        return "v" + this.major + "_" +this.minor + "_R" + (this.release < 1 ? "1" : release);
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s.%s", getMajor(), getMinor(), getBuild(), getRelease());
    }
}
