package com.captainbern.minecraft.reflection.providers.minecraft;

import com.captainbern.minecraft.reflection.providers.minecraft.remapper.RemapperException;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.MethodAccessor;
import org.bukkit.Bukkit;

public class RemapperReflectionProvider extends MinecraftReflectionProvider {

    protected ClassLoader classLoader;
    protected Object remapper;
    protected MethodAccessor<String> map;

    @Override
    public void initialize() {
        if (Bukkit.getServer() == null || !Bukkit.getVersion().contains("MCPC-Plus")) {
            throw new RemapperException(RemapperException.Reason.NOT_SUPPORTED);
        }

        this.remapper = new Reflection().reflect(this.classLoader.getClass()).getSafeFieldByName("remapper").getAccessor().get(this.classLoader);

        if (this.remapper == null) {
            throw new RemapperException(RemapperException.Reason.NOT_FOUND);
        }

        Class<?> remapperClass = this.remapper.getClass();
        this.map = new Reflection().reflect(remapperClass).getSafeMethod("map", String.class).getAccessor();
    }

    public String getRemappedName(final String className) {
        return this.map.invoke(this.remapper, className.replace('.', '/')).replace('/', '.');
    }
}
