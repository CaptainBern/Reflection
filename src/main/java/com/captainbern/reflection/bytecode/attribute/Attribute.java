package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class Attribute implements Opcode {

    protected String tag;

    protected int nameIndex;
    protected int length;
    protected ConstantPool constantPool;

    public Attribute(String tag, int index, int length, ConstantPool constantPool) {
        this.tag = tag;
        this.nameIndex = index;
        this.length = length;
        this.constantPool = constantPool;
    }

    public final String getTag() {
        return this.tag;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final int getLength() {
        return this.length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public String getName() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.nameIndex, CONSTANT_Utf8 );
        return constant.getString();
    }

    public static Attribute readAttribute(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        int index = codeStream.readUnsignedShort();
        Utf8Constant constant = (Utf8Constant) constantPool.getConstant(index, CONSTANT_Utf8);
        String tag = constant.getString();

        int length = codeStream.readInt();

        // switch (tag) {
        // Do stuff
        /**
         * Unknown
         */
        // }

        return null;
    }
}
