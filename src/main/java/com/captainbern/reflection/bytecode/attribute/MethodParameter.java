package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodParameter {

    private int nameIndex;
    private int accessFlags;

    public MethodParameter(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public MethodParameter(int nameIndex, int accessFlags) {
        this.nameIndex = nameIndex;
        this.accessFlags = accessFlags;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final int getAccessFlags() {
        return this.accessFlags;
    }

    public final void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }
}
