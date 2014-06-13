package com.captainbern.jbel;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.attribute.SourceFile;
import com.captainbern.jbel.commons.constant.Constant;
import com.captainbern.jbel.commons.constant.Utf8Constant;
import com.captainbern.jbel.commons.exception.ClassFormatException;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;
import com.captainbern.jbel.visitor.ClassVisitor;
import org.junit.Test;

import java.io.IOException;

public class ClassReaderTest implements Opcode {

    private final Object testField = new Object();

    public static void print(ClassReader reader) throws ClassFormatException {
        log("Magic: " + Integer.toHexString(reader.getMagic()));
        log("Compiler version: " + reader.getMajor() + "." + reader.getMinor());
        log("AccessFlags: " + Integer.toHexString(reader.getAccessFlags()));
        log("ClassName: " + reader.getClassName());
        log("SuperClassName: " + reader.getSuperClassName());
        for (Attribute attribute : reader.getAttributes()) {
            if (attribute instanceof SourceFile) {
                log("Compiled from: " + ((SourceFile) attribute).getSourceFile());
            }
        }

        log("");

        log("ConstantPool:");
        for (int i = 0; i < reader.getConstantPool().getSize(); i++) {
            Constant constant = reader.getConstantPool().getConstant(i);
            if (constant instanceof Utf8Constant) {
                log("\t" + ((Utf8Constant) constant).getString() + " index: " + i);
            }
        }

        log("");

        log("Interfaces:");
        for (Interface iface : reader.getInterfaces()) {
            log("\t" + iface.getName());
        }

        log("");

        log("Fields:");
        for (FieldInfo field : reader.getFields()) {
            log("\t Name: " + field.getName());
            log("\t AccessFlags: " + field.getAccessFlags());
            log("\t Signature: " + field.getDescriptor());
            log("\t Attributes:");
            for (Attribute attribute : field.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("");

        log("Methods:");
        for (MethodInfo method : reader.getMethods()) {
            log("\t Name: " + method.getName());
            log("\t AccessFlags: " + method.getAccessFlags());
            log("\t Signature: " + method.getDescriptor());
            log("\t Attributes:");
            for (Attribute attribute : method.getAttributes()) {
                log("\t\t" + attribute.getName());
            }
        }

        log("");

        log("Attributes");
        for (Attribute attribute : reader.getAttributes()) {
            log("\t" + attribute.getName());
        }
    }

    public static void log(Object message) {
        System.out.println(message);
    }

    @Test
    public void test() throws IOException, ClassFormatException {
        ClassReader reader = new ClassReader(HelloWorld.class.getCanonicalName());

        reader.accept(new ClassVisitor(JBEL_1) {
            @Override
            public void visit(int version, int access, String className, String superClassName, String signature) {
                log("Version: " + version);
                log("AccessFlags: " + Integer.toHexString(access));
                log("ClassName: " + className);
                log("SuperClassName: " + superClassName);
                log("Signature: " + signature);
                super.visit(version, access, className, superClassName, signature);
            }
        });

        print(reader);
    }
}
