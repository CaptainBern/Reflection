package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public class DescriptorConstant extends Constant {

    private int memberName;
    private int typeDescriptor;

    public DescriptorConstant(DescriptorConstant constant) {
        this(constant.getMemberName(), constant.getTypeDescriptor());
    }

    public DescriptorConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort(), stream.readUnsignedShort());
    }

    public DescriptorConstant(int memberName, int typeDescriptor) {
        super(CONSTANT_NameAndType);
        this.memberName = memberName;
        this.typeDescriptor = typeDescriptor;
    }

    public int getMemberName() {
        return this.memberName;
    }

    public int getTypeDescriptor() {
        return this.typeDescriptor;
    }
}
