package com.captainbern.reflection.bytecode.method;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.attribute.Attribute;
import com.captainbern.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class Method implements Opcode {
    protected int accessFlags;
    protected int nameIndex;
    protected int descriptorIndex;
    protected int attributeCount;
    protected Attribute[] attributes;

    protected ConstantPool constantPool;

    public Method(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), null, constantPool);
        this.attributeCount = codeStream.readUnsignedShort();
        this.attributes = new Attribute[attributeCount];
        for(int i = 0; i < attributeCount; i++) {
            this.attributes[i] = Attribute.readAttribute(codeStream, constantPool);
        }
    }

    public Method(int accessFlags, int nameIndex, int descriptorIndex, Attribute[] attributes, ConstantPool constantPool) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributeCount = attributes == null ? 0 : attributes.length;
        this.attributes = attributes;
        this.constantPool = constantPool;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public final int getAccessFlags() {
        return this.accessFlags;
    }

    public final void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final int getDescriptorIndex() {
        return this.descriptorIndex;
    }

    public final void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public final Attribute[] getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute[] attributes) {
        this.attributeCount = attributes == null ? 0 : attributes.length;
        this.attributes = attributes;
    }

    public final String getName() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.nameIndex, CONSTANT_Utf8);
        return constant.getString();
    }

    public final String getSignature() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.descriptorIndex, CONSTANT_Utf8);
        return constant.getString();
    }
}
