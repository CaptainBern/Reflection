package com.captainbern.minecraft.reflection;

import org.junit.Test;

public class MinecraftReflectionTest {

    @Test
    public void testReflection() {
        System.out.println(MinecraftReflection.getCraftEntityClass().getCanonicalName());
    }
}