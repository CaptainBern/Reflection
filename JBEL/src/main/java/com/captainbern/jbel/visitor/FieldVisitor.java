package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;

public class FieldVisitor {

    protected int api;

    protected FieldVisitor fieldVisitor;

    public FieldVisitor(int api) {
        this(api, null);
    }

    public FieldVisitor(int api, FieldVisitor fieldVisitor) {
        if (api != Opcode.JBEL_1) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.fieldVisitor = fieldVisitor;
    }

    public void visitEnd() {
        if (this.fieldVisitor != null) {
            this.fieldVisitor.visitEnd();
        }
    }
}
