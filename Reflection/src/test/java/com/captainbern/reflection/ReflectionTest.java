package com.captainbern.reflection;

import org.junit.Test;

import java.lang.reflect.Modifier;

import static com.captainbern.reflection.Reflection.reflect;
import static org.junit.Assert.assertFalse;

public class ReflectionTest {

    protected Object someObject = new Object();
    private int someInteger = 5;
    public String alohaString = "someString";

    public Integer someTestMethod() {
        return 1;
    }

    public Integer someTestMethod(Integer integer) {
        return integer;
    }

    @Test
    public void testAbstraction() {
        System.out.println(reflect(ReflectionTest.class).getMethod(null, Integer.class).getAccessor().invoke(this));
    }

    @Test
    public void testClasses() {
        SafeField field = reflect(Boolean.class).getFieldByName("TRUE");
        field.setModifiers(field.getModifiers() & ~(Modifier.FINAL));
        field.getAccessor().setStatic(false);
        assertFalse(Boolean.TRUE);
        System.out.println(Boolean.TRUE);
    }

    @Test
    public void testConstructors() {

    }

    @Test
    public void testMethods() {

    }

    @Test
    public void testFields() {

    }
}