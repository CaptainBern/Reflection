package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_MethodHandle;

public class MethodHandleConstant extends Constant {

    private int ckind;
    private int cindex;

    public MethodHandleConstant(MethodHandleConstant constant) {
        this(constant.getKind(), constant.getMethodIndex());
    }

    public MethodHandleConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedByte(), stream.readUnsignedShort());
    }

    public MethodHandleConstant(int ckind, int cindex) {
        super(CONSTANT_MethodHandle);
        this.ckind = ckind;
        this.cindex = cindex;
    }

    public int getKind() {
        return this.cindex;
    }

    public int getMethodIndex() {
        return this.cindex;
    }
}
