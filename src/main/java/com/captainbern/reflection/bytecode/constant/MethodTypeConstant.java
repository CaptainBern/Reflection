package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_MethodType;

public class MethodTypeConstant extends Constant {

    private int descriptor;

    public MethodTypeConstant(MethodTypeConstant constant) {
        this(constant.getDescriptor());
    }

    public MethodTypeConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public MethodTypeConstant(int descriptor) {
        super(CONSTANT_MethodType);
        this.descriptor = descriptor;
    }

    public int getDescriptor() {
        return this.descriptor;
    }
}
