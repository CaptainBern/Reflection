package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceFile extends Attribute implements Opcode {

    private int sourceFileIndex;

    public SourceFile(SourceFile sourceFile) {
        this(sourceFile.getNameIndex(), sourceFile.getLength(), sourceFile.getSourceFileIndex(), sourceFile.getConstantPool());
    }

    public SourceFile(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, codeStream.readUnsignedShort(), constantPool);
    }

    public SourceFile(int index, int length, int sourceFileIndex, ConstantPool constantPool) {
        super(ATTR_SOURCE_FILE, index, length, constantPool);
        this.sourceFileIndex = sourceFileIndex;
    }

    public final int getSourceFileIndex() {
        return this.sourceFileIndex;
    }

    public final void setSourceFileIndex(int sourceFileIndex) {
        this.sourceFileIndex = sourceFileIndex;
    }

    public String getSourceFile() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.sourceFileIndex, CONSTANT_Utf8);
        return constant.getString();
    }
}
