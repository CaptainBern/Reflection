package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class IntegerElementValue extends ElementValue {

    private int index;

    public IntegerElementValue(IntegerElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public IntegerElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public IntegerElementValue(int index, ConstantPool constantPool) {
        super(TYPE_INT, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final int getInteger() throws ClassFormatException {
        return getConstantPool().getIntegerConstant(this.index);
    }
}
