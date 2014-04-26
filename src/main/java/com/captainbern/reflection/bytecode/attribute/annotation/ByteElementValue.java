package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class ByteElementValue extends ElementValue {

    private int index;

    public ByteElementValue(ByteElementValue byteElementValue) {
        this(byteElementValue.getIndex(), byteElementValue.getConstantPool());
    }

    public ByteElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public ByteElementValue(int index, ConstantPool constantPool) {
        super(TYPE_BYTE, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int byteIndex) {
        this.index = byteIndex;
    }

    public final byte getByte() throws ClassFormatException {
        return (byte) getConstantPool().getIntegerConstant(this.index);
    }
}
