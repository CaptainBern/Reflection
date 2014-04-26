package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public class FieldConstant extends MemberConstant {

    public FieldConstant(FieldConstant constant) {
        this(constant.getClassIndex(), constant.getNameAndType());
    }

    public FieldConstant(DataInput stream) throws IOException {
        super(CONSTANT_Fieldref, stream);
    }

    public FieldConstant(int classIndex, int nameAndType) {
        super(CONSTANT_Fieldref, classIndex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Field";
    }
}
