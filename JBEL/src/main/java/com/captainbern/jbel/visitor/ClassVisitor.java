package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.member.Interface;

/**
 * An ASM-like visitor system.
 */
public abstract class ClassVisitor {

    protected int api;

    protected ClassVisitor classVisitor;

    public ClassVisitor(int api) {
        this(api, null);
    }

    public ClassVisitor(int api, ClassVisitor visitor) {
        if(api != Opcode.JBEL_1) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.classVisitor = visitor;
    }

    public void visit(int version, int access, String className, String superClassName, String signature) {
        if(this.classVisitor != null) {
            this.classVisitor.visit(version, access, className, superClassName, signature);
        }
    }

    public void visitSource(String source) {
        if(this.classVisitor != null) {
            this.classVisitor.visitSource(source);
        }
    }

    public Interface visitInterface(Interface iface) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitInterface(iface);
        }
        return null;
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitField(access, name, descriptor, signature);
        }
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if(this.classVisitor != null) {
            this.classVisitor.visitAttribute(attribute);
        }
    }

    public void visitEnd() {
        if(this.classVisitor != null) {
            this.classVisitor.visitEnd();
        }
    }
}
