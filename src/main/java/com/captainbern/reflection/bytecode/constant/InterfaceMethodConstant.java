package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_InterfaceMethodref;

public class InterfaceMethodConstant extends MemberConstant {

    public InterfaceMethodConstant(InterfaceMethodConstant constant) {
        this(constant.getClassIndex(), constant.getNameAndType());
    }

    public InterfaceMethodConstant(DataInput stream) throws IOException {
        super(CONSTANT_InterfaceMethodref, stream);
    }

    public InterfaceMethodConstant(int cindex, int nameAndType) {
        super(CONSTANT_InterfaceMethodref, cindex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Interface";
    }
}
