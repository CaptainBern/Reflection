package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

public class ParameterAnnotationEntry {

    private int tableLength;
    private AnnotationElementValue[] annotationElementValues;

    public ParameterAnnotationEntry(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this.tableLength = codeStream.readUnsignedShort();
        this.annotationElementValues = new AnnotationElementValue[this.tableLength];
        for (int i = 0; i < this.tableLength; i++) {
            // TODO isRuntimeVisible
            this.annotationElementValues[i] = AnnotationElementValue.read(codeStream, constantPool, false);
        }
    }

    public final int getTableLength() {
        return this.tableLength;
    }

    public final AnnotationElementValue[] getAnnotations() {
        return this.annotationElementValues;
    }
}
