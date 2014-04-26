package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class ShortElementValue extends ElementValue {

    private int index;

    public ShortElementValue(ShortElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public ShortElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public ShortElementValue(int index, ConstantPool constantPool) {
        super(TYPE_SHORT, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final short getShort() throws ClassFormatException {
        return (short) getConstantPool().getIntegerConstant(this.index);
    }
}
