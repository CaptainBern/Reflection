package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

public class ClassElementValue extends ElementValue {

    private int classIndex;

    public ClassElementValue(ClassElementValue classElementValue) {
        this(classElementValue.getIndex(), classElementValue.getConstantPool());
    }

    public ClassElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public ClassElementValue(int classIndex, ConstantPool constantPool) {
        super(ElementValue.TYPE_CLASS, constantPool);
        this.classIndex = classIndex;
    }

    public final int getIndex() {
        return this.classIndex;
    }

    public final void setIndex(int classIndex) {
        this.classIndex = classIndex;
    }
}
