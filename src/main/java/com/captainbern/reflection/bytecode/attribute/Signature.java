package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class Signature extends Attribute {

    private int signatureIndex;

    public Signature(Signature signature) {
        this(signature.getNameIndex(), signature.getLength(), signature.getSignatureIndex(), signature.getConstantPool());
    }

    public Signature(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, codeStream.readUnsignedShort(), constantPool);
    }

    public Signature(int index, int length, int signatureIndex, ConstantPool constantPool) {
        super(ATTR_SIGNATURE, index, length, constantPool);
        this.signatureIndex = signatureIndex;
    }

    public final int getSignatureIndex() {
        return this.signatureIndex;
    }

    public final void setSignatureIndex(int signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    public String getSignature() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.signatureIndex, CONSTANT_Utf8);
        return constant.getString();
    }
}
