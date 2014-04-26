package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_InvokeDynamic;

public class InvokeDynamicConstant extends Constant {

    private int bootstrap;
    private int nameAndType;

    public InvokeDynamicConstant(InvokeDynamicConstant constant) {
        this(constant.getBootstrap(), constant.getNameAndType());
    }

    public InvokeDynamicConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort(), stream.readUnsignedShort());
    }

    public InvokeDynamicConstant(int bootstrap, int nameAndType) {
        super(CONSTANT_InvokeDynamic);
        this.bootstrap = bootstrap;
        this.nameAndType = nameAndType;
    }

    public int getBootstrap() {
        return this.bootstrap;
    }

    public int getNameAndType() {
        return this.nameAndType;
    }
}
