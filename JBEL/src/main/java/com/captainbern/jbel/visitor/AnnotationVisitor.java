package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;

public class AnnotationVisitor {

    protected int api;

    protected AnnotationVisitor annotationVisitor;

    public AnnotationVisitor(int api, AnnotationVisitor annotationVisitor) {
        if(api != Opcode.JBEL_1) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.annotationVisitor = annotationVisitor;
    }

    public void visitEnd() {
        if(this.annotationVisitor != null) {
            this.annotationVisitor.visitEnd();
        }
    }
}
