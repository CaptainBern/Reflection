package com.captainbern.reflection.bytecode;

import com.captainbern.reflection.bytecode.exception.ClassFormatException;
import com.captainbern.reflection.utils.ClassUtils;
import org.junit.Test;

import java.io.IOException;

public class ClassFileTest {

    @Test
    public void testBytecode() throws IOException, ClassFormatException {
        ClassFile classFile = new ClassFile(ClassUtils.classToBytes(ClassFileTest.class.getCanonicalName()));
        log("Class name = " + classFile.getClassName());
        log("Super class = " + classFile.getSuperClassName());
        log("Access flags = 0x" + Integer.toHexString(classFile.getAccessFlags()));
    }

    private void log(Object message) {
        System.out.println(message);
    }
}
