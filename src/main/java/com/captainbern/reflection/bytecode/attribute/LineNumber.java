package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumber {

    private int startPC;
    private int lineNumber;

    public LineNumber(LineNumber lineNumber) {
        this(lineNumber.getStartPC(), lineNumber.getLineNumber());
    }

    public LineNumber(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public LineNumber(int startPC, int lineNumber) {
        this.startPC = startPC;
        this.lineNumber = lineNumber;
    }

    public final int getStartPC() {
        return this.startPC;
    }

    public final void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public final int getLineNumber() {
        return this.lineNumber;
    }

    public final void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
