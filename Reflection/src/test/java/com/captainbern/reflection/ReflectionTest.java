package com.captainbern.reflection;

public class ReflectionTest {
  /*
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentNull() {
        new Reflection().reflect((Class) null);
    }

    @Test
    public void testLetsHackBoolean() {
        SafeField<Boolean> FALSE =  new Reflection().reflect(Boolean.class).getSafeFieldByName("FALSE");
        FALSE.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
        FALSE.getAccessor().set(null, true);
        assertTrue(Boolean.FALSE);
    } */
}