package com.captainbern.jbel.visitor;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;

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

    public void visitConstantPool(ConstantPool constantPool) {
        if(this.classVisitor != null) {
            this.classVisitor.visitConstantPool(constantPool);
        }
    }

    public Interface visitInterface(Interface iface) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitInterface(iface);
        }
        return null;
    }

    public void visitInterfaces(Interface[] interfaces) {
        if(this.classVisitor != null) {
            this.classVisitor.visitInterfaces(interfaces);
        }
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitField(access, name, descriptor, signature);
        }
        return null;
    }

    public void visitFields(FieldInfo[] fields) {
        if(this.classVisitor != null) {
            this.classVisitor.visitFields(fields);
        }
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(this.classVisitor != null) {
            return this.classVisitor.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return null;
    }

    public void visitMethods(MethodInfo[] methods) {
        if(this.classVisitor != null) {
            this.classVisitor.visitMethods(methods);
        }
    }

    public void visitAttribute(Attribute attribute) {
        if(this.classVisitor != null) {
            this.classVisitor.visitAttribute(attribute);
        }
    }

    public void visitAttributes(Attribute[] attributes) {
        if(this.classVisitor != null) {
            this.classVisitor.visitAttributes(attributes);
        }
    }

    public void visitEnd() {
        if(this.classVisitor != null) {
            this.classVisitor.visitEnd();
        }
    }
}
