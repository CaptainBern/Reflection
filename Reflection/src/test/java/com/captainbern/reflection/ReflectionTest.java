package com.captainbern.reflection;

import org.junit.Test;

import java.lang.reflect.Modifier;

import static com.captainbern.reflection.Reflection.reflect;
import static org.junit.Assert.assertFalse;

public class ReflectionTest {

    @Test
    public void testClasses() {
        SafeField field = reflect(Boolean.class).getFieldByName("TRUE");
        field.setModifiers(field.getModifiers() & ~(Modifier.FINAL));
        field.getAccessor().setStatic(Boolean.FALSE);
        assertFalse(Boolean.TRUE);
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