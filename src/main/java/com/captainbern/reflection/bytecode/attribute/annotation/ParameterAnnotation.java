package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.attribute.Attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class ParameterAnnotation extends Attribute {

    private int paramTableLength;
    private ParameterAnnotationEntry[] parameterAnnotationEntries;

    public ParameterAnnotation(String tag, int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(tag, index, length, (ParameterAnnotationEntry[]) null, constantPool);
        this.paramTableLength = codeStream.readUnsignedByte();
        this.parameterAnnotationEntries = new ParameterAnnotationEntry[this.paramTableLength];
        for(int i = 0; i < this.paramTableLength; i++) {
            this.parameterAnnotationEntries[i] = new ParameterAnnotationEntry(codeStream, constantPool);
        }
    }

    public ParameterAnnotation(String tag, int index, int length, ParameterAnnotationEntry[] entries, ConstantPool constantPool) {
        super(tag, index, length, constantPool);
        setParameterAnnotationEntries(entries);
    }

    public final void setParameterAnnotationEntries(ParameterAnnotationEntry[] entries) {
        this.parameterAnnotationEntries = entries;
        this.paramTableLength = entries == null ? 0 : entries.length;
    }

    public final ParameterAnnotationEntry[] getParameterAnnotationEntries() {
        return this.parameterAnnotationEntries;
    }

    public final int getParamTableLength() {
        return this.paramTableLength;
    }
}
