package com.captainbern.reflection.bytecode;

import com.captainbern.reflection.bytecode.attribute.Attribute;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;
import com.captainbern.reflection.bytecode.member.Interface;
import com.captainbern.reflection.bytecode.member.field.FieldInfo;
import com.captainbern.reflection.bytecode.member.method.MethodInfo;
import org.junit.Test;

import java.io.IOException;

public class ClassReaderTest implements Opcode {

    private static String CLASS_PREFIX = "$JBLGeneratedClass";
    private static Object object = new Object();

    @Test
    public void testBytecode() throws IOException, ClassFormatException {
        ClassReader classReader = new ClassReader(ClassReaderTest.class.getCanonicalName());
        log("Class name: " + classReader.getClassName());
        log("Super class: " + classReader.getSuperClassName());
        log("Access flags: 0x" + Integer.toHexString(classReader.getAccessFlags()));
        log("-------------------------------------------------------");

        log("Interfaces: ");
        for(Interface iface : classReader.getInterfaces()) {
            log("\t" + iface.getName());
        }

        log("Methods:");
        for(MethodInfo method : classReader.getMethods()) {
            log("\t" + method.getName() + " - " + method.getSignature());
            for(Attribute attribute : method.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("Fields:");
        for(FieldInfo field : classReader.getFields()) {
            log("\t" + field.getName() + " - " + field.getSignature());
            for(Attribute attribute : field.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("Attributes:");
        for(Attribute attribute : classReader.getAttributes()) {
            log("\t" + attribute.getName());
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }
}
