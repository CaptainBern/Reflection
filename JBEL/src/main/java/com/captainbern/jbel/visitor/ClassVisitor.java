package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;

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

    public void visit(int access, String className, String superClassName, String[] interfaces) {
        if(this.classVisitor != null) {
            this.classVisitor.visit(access, className, superClassName, interfaces);
        }
    }
}
