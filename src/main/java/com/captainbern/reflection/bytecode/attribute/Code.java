package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;

public class Code extends Attribute implements Opcode {

    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private byte[] code;
    private int exceptionTableLength;
    private Exception[] exceptionTable;
    private int attributeCount;
    private Attribute[] attributes;

    public Code(int index, int length, DataInputStream codeStream, ConstantPool constantPool) {
        super(ATTR_CODE, index, length, constantPool);
    }

    public Code(int index, int length, int maxStack, int maxLocals, byte[] code, Exception[] exceptionTable, Attribute[] attributes, ConstantPool constantPool) {
        super(ATTR_CODE, index, length, constantPool);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        setCode(code);
        setExceptionTable(exceptionTable);
        setAttributes(attributes);
    }

    private final int getInternalLength() {
        return 8 + codeLength + 2 + 8 * exceptionTableLength + 2;
    }

    private final int calculateLength() {
        int len = 0;
        for (int i = 0; i < attributeCount; i++) {
            len += attributes[i].length + 6 /*attribute header size*/;
        }
        return len + getCodeLength();
    }

    public final int getMaxStack() {
        return this.maxStack;
    }

    public final void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
        this.length = calculateLength();
    }

    public final int getMaxLocals() {
        return this.maxLocals;
    }

    public final void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public final int getCodeLength() {
        return this.codeLength;
    }

    public final byte[] getCode() {
        return this.code;
    }

    public final void setCode(byte[] code) {
        this.codeLength = code == null ? 0 : code.length;
        this.code = code;
        this.length = calculateLength();
    }

    public final int getExceptionTableLength() {
        return this.exceptionTableLength;
    }

    public final Exception[] getExceptionTable() {
        return this.exceptionTable;
    }

    public final void setExceptionTable(Exception[] exceptionTable) {
        this.exceptionTableLength = exceptionTable == null ? 0 : exceptionTable.length;
        this.exceptionTable = exceptionTable;
        this.length = calculateLength();
    }

    public final int getAttributeCount() {
        return this.attributeCount;
    }

    public final void setAttributeCount(int attributeCount) {
        this.attributeCount = attributeCount;
    }

    public final Attribute[] getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute[] attributes) {
        this.attributeCount = attributes == null ? 0 : attributes.length;
        this.attributes = attributes;
        this.length = calculateLength();
    }

    public LineNumberTable getLineNumberTable() {
        for (int i = 0; i < attributeCount; i++) {
            if (attributes[i] instanceof LineNumberTable) {
                return (LineNumberTable) attributes[i];
            }
        }
        return null;
    }

    public LocalVariableTable getLocalVariableTable() {
        for (int i = 0; i < attributeCount; i++) {
            if (attributes[i] instanceof LocalVariableTable) {
                return (LocalVariableTable) attributes[i];
            }
        }
        return null;
    }
}
