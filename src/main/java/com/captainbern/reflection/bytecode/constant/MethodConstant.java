package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_Methodref;

public class MethodConstant extends MemberConstant {

    public MethodConstant(MethodConstant constant) {
        this(constant.getClassIndex(), constant.getNameAndType());
    }

    public MethodConstant(DataInput stream) throws IOException {
        super(CONSTANT_Methodref, stream);
    }

    public MethodConstant(int cindex, int nameAndType) {
        super(CONSTANT_Methodref, cindex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Method";
    }
}
