package com.captainbern.jbel.commons.attribute.stackmap;

import com.captainbern.jbel.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StackMapEntry {

    private int offset;
    private int numberOfLocals;
    private TypeInfo[] locals;
    private int numberOfStackItems;
    private TypeInfo[] stack;
    private ConstantPool constantPool;

    public StackMapEntry(final DataInputStream codeStream, final ConstantPool constantPool) throws IOException {
        this(codeStream.readShort(), codeStream.readShort(), null, -1, null, constantPool);
        this.locals = new TypeInfo[this.numberOfLocals];
        for (int i = 0; i < this.numberOfLocals; i++) {
            this.locals[i] = new TypeInfo(codeStream, constantPool);
        }
        this.numberOfStackItems = codeStream.readShort();
        this.stack = new TypeInfo[this.numberOfStackItems];
        for (int i = 0; i < this.numberOfStackItems; i++) {
            this.stack[i] = new TypeInfo(codeStream, constantPool);
        }
    }


    public StackMapEntry(int offset, int numberOfLocals, TypeInfo[] locals, int numberOfStackItems, TypeInfo[] stack, ConstantPool constantPool) {
        this.offset = offset;
        this.numberOfLocals = numberOfLocals;
        this.locals = locals;
        this.numberOfStackItems = numberOfStackItems;
        this.stack = stack;
        this.constantPool = constantPool;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumberOfLocals() {
        return this.numberOfLocals;
    }

    public void setNumberOfLocals(int numberOfLocals) {
        this.numberOfLocals = numberOfLocals;
    }

    public TypeInfo[] getLocals() {
        return this.locals;
    }

    public void setLocals(TypeInfo[] locals) {
        this.locals = locals;
    }

    public int getNumberOfStackItems() {
        return this.numberOfStackItems;
    }

    public void setNumberOfStackItems(int numberOfStackItems) {
        this.numberOfStackItems = numberOfStackItems;
    }

    public TypeInfo[] getStack() {
        return this.stack;
    }

    public void setStack(TypeInfo[] stack) {
        this.stack = stack;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void write(final DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.offset);

        codeStream.writeShort(this.numberOfLocals);
        for (int i = 0; i < this.numberOfLocals; i++) {
            this.locals[i].write(codeStream);
        }

        codeStream.writeShort(this.numberOfStackItems);
        for (int i = 0; i < this.numberOfStackItems; i++) {
            this.stack[i].write(codeStream);
        }
    }
}
