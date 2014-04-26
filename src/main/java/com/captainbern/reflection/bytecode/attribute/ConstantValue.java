package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.*;

public class ConstantValue extends Attribute {

    private int constantValueIndex;

    public ConstantValue(ConstantValue constantValue) {
        this(constantValue.getNameIndex(), constantValue.getLength(), constantValue.getConstantValueIndex(), constantValue.getConstantPool());
    }

    public ConstantValue(int nameIndex, int length, DataInputStream dataInputStream, ConstantPool constantPool) throws IOException {
        this(nameIndex, length, dataInputStream.readUnsignedShort(), constantPool);
    }

    public ConstantValue(int nameIndex, int length, int constantValueIndex, ConstantPool constantPool) {
        super(ATTR_CONSTANT_VALUE, nameIndex, length, constantPool);
        this.constantValueIndex = constantValueIndex;
    }

    public final int getConstantValueIndex() {
        return this.constantValueIndex;
    }

    public final void setConstantValueIndex(int index) {
        this.constantValueIndex = index;
    }
}

