package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public class ClassConstant extends Constant {

    private int name;

    public ClassConstant(ClassConstant classConstant) {
        this(classConstant.getNameIndex());
    }

    public ClassConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public ClassConstant(int name) {
        super(CONSTANT_Class);
        this.name = name;
    }

    public int getNameIndex() {
        return this.name;
    }
}
