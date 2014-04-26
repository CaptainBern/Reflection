package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.constant.ClassConstant;
import com.captainbern.reflection.bytecode.constant.DescriptorConstant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class EnclosingMethod extends Attribute implements Opcode {

    private int classIndex;
    private int methodIndex;

    public EnclosingMethod(EnclosingMethod enclosingMethod) {
        this(enclosingMethod.getNameIndex(), enclosingMethod.getLength(), enclosingMethod.getClassIndex(), enclosingMethod.getMethodIndex(), enclosingMethod.getConstantPool());
    }

    public EnclosingMethod(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), constantPool);
    }

    public EnclosingMethod(int index, int length, int classIndex, int methodIndex, ConstantPool constantPool) {
        super(ATTR_ENCLOSING_METHOD, index, length, constantPool);
        this.classIndex = classIndex;
        this.methodIndex = methodIndex;
    }

    public final int getClassIndex() {
        return this.classIndex;
    }

    public final void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }

    public final int getMethodIndex() {
        return this.methodIndex;
    }

    public final void setMethodIndex(int methodIndex) {
        this.methodIndex = methodIndex;
    }

    public final ClassConstant getClassConstant() throws ClassFormatException {
        ClassConstant constant = (ClassConstant) this.constantPool.getConstant(this.classIndex, CONSTANT_Class);
        return constant;
    }

    public final DescriptorConstant getMethodNameAndTypeConstant() throws ClassFormatException {
        DescriptorConstant constant = (DescriptorConstant) this.constantPool.getConstant(this.methodIndex, CONSTANT_NameAndType);
        return constant;
    }
}
