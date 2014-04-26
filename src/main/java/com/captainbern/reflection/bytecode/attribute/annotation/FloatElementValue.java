package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class FloatElementValue extends ElementValue {

    private int index;

    public FloatElementValue(FloatElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public FloatElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public FloatElementValue(int index, ConstantPool constantPool) {
        super(TYPE_FLOAT, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final float getFloat() throws ClassFormatException {
        return getConstantPool().getFloatConstant(this.index);
    }
}
