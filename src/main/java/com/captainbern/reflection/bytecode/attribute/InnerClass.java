package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class InnerClass {

    private int innerClassInfoIndex;
    private int outerClassInfoIndex;
    private int innerNameIndex;
    private int innerClassAccessFlags;

    public InnerClass(InnerClass innerClass) {
        this(innerClass.getInnerClassInfoIndex(), innerClass.getOuterClassInfoIndex(), innerClass.getInnerNameIndex(), innerClass.getInnerClassAccessFlags());
    }

    public InnerClass(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public InnerClass(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
        this.innerClassInfoIndex = innerClassInfoIndex;
        this.outerClassInfoIndex = outerClassInfoIndex;
        this.innerNameIndex = innerNameIndex;
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public final int getInnerClassInfoIndex() {
        return this.innerClassInfoIndex;
    }

    public final int getOuterClassInfoIndex() {
        return this.outerClassInfoIndex;
    }

    public final int getInnerNameIndex() {
        return this.innerNameIndex;
    }

    public final int getInnerClassAccessFlags() {
        return this.innerClassAccessFlags;
    }

    public final void setInnerClassInfoIndex(int innerClassInfoIndex) {
        this.innerClassInfoIndex = innerClassInfoIndex;
    }

    public final void setOuterClassInfoIndex(int outerClassInfoIndex) {
        this.outerClassInfoIndex = outerClassInfoIndex;
    }

    public final void setInnerNameIndex(int innerNameIndex) {
        this.innerNameIndex = innerNameIndex;
    }

    public final void setInnerClassAccessFlags(int innerClassAccessFlags) {
        this.innerClassAccessFlags = innerClassAccessFlags;
    }
}
