package com.captainbern.reflection;

import org.junit.Test;

public class ReflectionTest {

    private Object object = new Object();
    public Integer someInteger = 4;
    protected ReflectionTest test;

    @Test
    public void testReflection() {
        test = this;
        ReflectedClass reflectedClass = Reflection.reflect(ReflectionTest.class);
        for(Object field : reflectedClass.getFields()) {
            System.out.println(((ReflectedField) field).getName());
        }
    }
}