package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public abstract class MemberConstant extends Constant {

    private int classIndex;
    private int nameAndType;

    public MemberConstant(MemberConstant memberConstant) {
        this(memberConstant.getTag(), memberConstant.getClassIndex(), memberConstant.getNameAndType());
    }

    public MemberConstant(byte tag, DataInput stream) throws IOException {
        this(tag, stream.readUnsignedShort(), stream.readUnsignedShort());
    }

    public MemberConstant(byte tag, int cindex, int nameAndType) {
        super(tag);
        this.classIndex = cindex;
        this.nameAndType = nameAndType;
    }

    public int getClassIndex() {
        return this.classIndex;
    }

    public int getNameAndType() {
        return this.nameAndType;
    }

    public abstract String getTagName();
}
