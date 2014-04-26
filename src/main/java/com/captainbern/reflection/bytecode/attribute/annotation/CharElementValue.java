package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class CharElementValue extends ElementValue {

    private int index;

    public CharElementValue(CharElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public CharElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public CharElementValue(int index, ConstantPool constantPool) {
        super(TYPE_CHAR, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final char getChar() throws ClassFormatException {
        return (char) getConstantPool().getIntegerConstant(this.index);
    }
}
