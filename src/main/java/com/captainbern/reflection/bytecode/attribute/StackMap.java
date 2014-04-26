package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;

public class StackMap extends Attribute implements Opcode {

    private int mapLength;

    public StackMap(int index, int length, DataInputStream codeStream, ConstantPool constantPool) {
        super(ATTR_STACK_MAP, index, length, constantPool);
    }
}
