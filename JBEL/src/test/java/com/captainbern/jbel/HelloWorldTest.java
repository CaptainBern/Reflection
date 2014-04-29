package com.captainbern.jbel;

import com.captainbern.jbel.commons.constant.Constant;
import com.captainbern.jbel.commons.constant.Utf8Constant;
import com.captainbern.jbel.commons.exception.ClassFormatException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloWorldTest {

    @Test
    public void test() throws IOException, ClassFormatException {
        ClassReader reader = new ClassReader(HelloWorld.class.getCanonicalName());

        for(Constant constant : reader.getConstantPool().getConstantPool()) {
            if(constant instanceof Utf8Constant) {
                if(((Utf8Constant) constant).getString().equals("Hello World!"))
                    ((Utf8Constant) constant).setString("Changed!");
                continue;
            }
        }

        Class<?> test = reader.defineClass();
        try {
            Method method = test.getMethod("main");
            method.invoke(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
