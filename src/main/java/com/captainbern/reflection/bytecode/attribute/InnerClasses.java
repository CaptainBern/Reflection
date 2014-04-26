package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class InnerClasses extends Attribute implements Opcode {

    private int numberOfInnerClasses;
    private InnerClass[] innerClasses;

    public InnerClasses(InnerClasses innerClasses) {
        this(innerClasses.getNameIndex(), innerClasses.getLength(), innerClasses.getInnerClasses(), innerClasses.getConstantPool());
    }

    public InnerClasses(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (InnerClass[]) null, constantPool);
        this.numberOfInnerClasses = codeStream.readUnsignedShort();
        this.innerClasses = new InnerClass[this.numberOfInnerClasses];
        for(int i = 0; i < this.numberOfInnerClasses; i++) {
            this.innerClasses[i] = new InnerClass(codeStream);
        }
    }

    public InnerClasses(int index, int length, InnerClass[] innerClasses, ConstantPool constantPool) {
        super(ATTR_INNER_CLASSES, index, length, constantPool);
        setInnerClasses(innerClasses);
    }

    public final int getNumberOfInnerClasses() {
        return this.numberOfInnerClasses;
    }

    public final InnerClass[] getInnerClasses() {
        return this.innerClasses;
    }

    public final void setInnerClasses(InnerClass[] innerClasses) {
        this.numberOfInnerClasses = innerClasses == null ? 0 : innerClasses.length;
        this.innerClasses = innerClasses;
    }
}
