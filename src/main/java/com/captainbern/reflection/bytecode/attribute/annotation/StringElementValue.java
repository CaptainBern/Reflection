package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class StringElementValue extends ElementValue {

    private int index;

    public StringElementValue(StringElementValue stringElementValue) {
        this(stringElementValue.getIndex(), stringElementValue.getConstantPool());
    }

    public StringElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public StringElementValue(int index, ConstantPool constantPool) {
        super(TYPE_STRING, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int stringIndex) {
        this.index = stringIndex;
    }

    public final String getString() throws ClassFormatException {
        return getConstantPool().getUtf8StringConstant(this.index);
    }
}
