package com.captainbern.reflection;

import org.junit.Test;

import static com.captainbern.reflection.Reflection.reflect;

public class ReflectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentNull() {
        reflect((Class) null);
    }
}