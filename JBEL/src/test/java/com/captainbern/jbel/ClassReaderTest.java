package com.captainbern.jbel;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.exception.ClassFormatException;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;
import org.junit.Test;

import java.io.IOException;

public class ClassReaderTest implements Opcode {

    private final Object testField = new Object();

    @Test
    public void test() throws IOException, ClassFormatException {
        ClassReader reader = new ClassReader(ClassReaderTest.class.getCanonicalName());
        log("Magic: " + reader.getMagic());
        log("Minor: " + reader.getMinor());
        log("Major: " + reader.getMajor());
        log("AccessFlags: " + reader.getAccessFlags());
        log("ClassName: " + reader.getClassName());
        log("SuperClassName: " + reader.getSuperClassName());

        log("");

        log("Interfaces:");
        for(Interface iface : reader.getInterfaces()) {
            log("\t" + iface.getName());
        }

        log("");

        log("Fields:");
        for(FieldInfo field : reader.getFields()) {
            log("\t Name: " + field.getName());
            log("\t AccessFlags: " + field.getAccessFlags());
            log("\t Signature: " + field.getSignature());
            log("\t Attributes:");
            for(Attribute attribute : field.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("");

        log("Methods:");
        for(MethodInfo method : reader.getMethods()) {
            log("\t Name: " + method.getName());
            log("\t AccessFlags: " + method.getAccessFlags());
            log("\t Signature: " + method.getSignature());
            log("\t Attributes:");
            for(Attribute attribute : method.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("");

        log("Attributes");
        for(Attribute attribute : reader.getAttributes()) {
            log("\t" + attribute.getName());
        }
    }

    public static void log(Object message) {
        System.out.println(message);
    }
}
