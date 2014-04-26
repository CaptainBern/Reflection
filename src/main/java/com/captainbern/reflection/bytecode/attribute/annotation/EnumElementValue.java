package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

public class EnumElementValue extends ElementValue {

    private int typeIndex;
    private int valueIndex;

    public EnumElementValue(EnumElementValue enumElementValue) {
        this(enumElementValue.getTypeIndex(), enumElementValue.getValueIndex(), enumElementValue.getConstantPool());
    }

    public EnumElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), constantPool);
    }

    public EnumElementValue(int typeIndex, int valueIndex, ConstantPool constantPool) {
        super(ElementValue.TYPE_ENUM, constantPool);
        this.typeIndex = typeIndex;
        this.valueIndex = valueIndex;
    }

    public final int getTypeIndex() {
        return this.typeIndex;
    }

    public final void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public final int getValueIndex() {
        return this.valueIndex;
    }

    public final void setValueIndex(int valueIndex) {
        this.valueIndex = valueIndex;
    }
}
