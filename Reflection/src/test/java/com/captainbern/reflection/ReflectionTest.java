package com.captainbern.reflection;

import org.junit.Test;

import static com.captainbern.reflection.matcher.Matchers.withExactName;

public class ReflectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentNull() {
        System.out.println(new Reflection().reflect(Byte.class).getSafeFieldByName("TYPE").getType().getMethods(withExactName("toString")).get(0).getName());
        new Reflection().reflect((Class) null);
    }
}