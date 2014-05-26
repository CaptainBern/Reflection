package com.captainbern.minecraft.reflection.providers.minecraft;

import com.captainbern.minecraft.reflection.providers.minecraft.remapper.RemapperException;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.provider.impl.DefaultReflectionProvider;
import com.captainbern.reflection.provider.type.ClassProvider;
import org.bukkit.Bukkit;

public class MinecraftReflectionProvider extends DefaultReflectionProvider {

    public MinecraftReflectionProvider() {

    }

    public void initialize() {
        if (Bukkit.getVersion() == null || !Bukkit.getServer().getVersion().contains("MCPC-Plus")) {
             throw new RemapperException(RemapperException.Reason.NOT_SUPPORTED);
        }
    }

    @Override
    public <T> ClassProvider<T> getClassProvider(Reflection reflection, Class<T> clazz, boolean forceAccess) {
        return null;
    }

    @Override
    public <T> ClassProvider<T> getClassProvider(Reflection reflection, String className, boolean forceAccess) {
        return null;
    }
}
