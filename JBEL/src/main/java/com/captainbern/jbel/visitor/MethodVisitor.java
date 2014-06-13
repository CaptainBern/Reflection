package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Attribute;

public class MethodVisitor {

    protected int api;

    protected MethodVisitor methodVisitor;

    public MethodVisitor(int api, MethodVisitor methodVisitor) {
        if (api != Opcode.JBEL_1) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.methodVisitor = methodVisitor;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.methodVisitor != null) {
            this.methodVisitor.visitAttribute(attribute);
        }
    }

    public void visitEnd() {
        if (this.methodVisitor != null) {
            this.methodVisitor.visitEnd();
        }
    }
}
