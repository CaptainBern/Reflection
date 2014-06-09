package com.captainbern.minecraft.conversion;

import java.util.HashMap;
import java.util.Map;

public class BukkitConverters implements Converter {

    private static BukkitConverters instance = new BukkitConverters();

    private Map<Class<?>, Converter> converterMap = new HashMap<>();

    public static BukkitConverters getInstance() {
        return instance;
    }

    private BukkitConverters() {

    }

    @Override
    public Object convert(Object bukkitHandle) {
        return null;
    }
}
