package com.captainbern.reflection.bytecode;

import com.captainbern.reflection.bytecode.attribute.Attribute;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;
import com.captainbern.reflection.bytecode.field.FieldInfo;
import com.captainbern.reflection.bytecode.method.MethodInfo;
import com.captainbern.reflection.utils.ClassUtils;
import org.junit.Test;

import java.io.IOException;

public class ClassFileTest {

    private static String CLASS_PREFIX = "$JBLGeneratedClass";
    private static Object object = new Object();

    @Test
    public void testBytecode() throws IOException, ClassFormatException {
        ClassFile classFile = new ClassFile(ClassUtils.classToBytes(ClassFile.class.getCanonicalName()));
        log("Class name: " + classFile.getClassName());
        log("Super class: " + classFile.getSuperClassName());
        log("Access flags: 0x" + Integer.toHexString(classFile.getAccessFlags()));
        log("-------------------------------------------------------");

        log("Methods:");
        for(MethodInfo method : classFile.getMethods()) {
            log("\t" + method.getName() + " - " + method.getSignature());
            for(Attribute attribute : method.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("Fields:");
        for(FieldInfo field : classFile.getFields()) {
            log("\t" + field.getName() + " - " + field.getSignature());
            for(Attribute attribute : field.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("Attributes:");
        for(Attribute attribute : classFile.getAttributes()) {
            log("\t" + attribute.getName());
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }
}
