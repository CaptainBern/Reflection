package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class LongElementValue extends ElementValue {

    private int index;

    public LongElementValue(LongElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public LongElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public LongElementValue(int index, ConstantPool constantPool) {
        super(TYPE_LONG, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final long getLong() throws ClassFormatException {
        return getConstantPool().getLongConstant(this.index);
    }
}
