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

    private enum Test {

        SOME_ENUM("Test");

        final String value;

        private Test(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

        ClassTemplate<Test> template = new Reflection().reflect(Test.class);

        for (SafeField field : template.getSafeFields()) {
            System.out.println(field.getName());
        }

        System.out.println("Injecting a new Enum type!");

        EnumModifier<Test> enumModifier = new Reflection().newEnumModifier(Test.class);

        enumModifier.addEnumValue("ANOTHER_ENUM", new Object[]{"Test Passed!"}, new Class[]{String.class});

        System.out.println("Injection done");
        System.out.println("---------------");

        for (Test val : Test.values()) {
            System.out.println(val.name());
        }
    }
}