package com.captainbern.reflection;

import org.junit.Test;

public class ReflectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentNull() {
        new Reflection().reflect((Class) null);
    }
}