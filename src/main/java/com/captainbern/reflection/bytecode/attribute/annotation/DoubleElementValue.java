package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class DoubleElementValue extends ElementValue {

    private int index;

    public DoubleElementValue(DoubleElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public DoubleElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public DoubleElementValue(int index, ConstantPool constantPool) {
        super(TYPE_DOUBLE, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final double getDouble() throws ClassFormatException {
        return getConstantPool().getDoubleConstant(this.index);
    }
}
