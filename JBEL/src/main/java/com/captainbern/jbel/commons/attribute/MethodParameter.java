package com.captainbern.jbel.commons.attribute;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    public int getNameIndex() {
        return this.nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.nameIndex);
        codeStream.writeShort(this.accessFlags);
    }
}
