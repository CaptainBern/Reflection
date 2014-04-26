package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class BooleanElementValue extends ElementValue {

    private int index;

    public BooleanElementValue(BooleanElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public BooleanElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public BooleanElementValue(int index, ConstantPool constantPool) {
        super(TYPE_BOOLEAN, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final boolean getBoolean() throws ClassFormatException {
        return getConstantPool().getIntegerConstant(this.index) != 0;
    }
}
