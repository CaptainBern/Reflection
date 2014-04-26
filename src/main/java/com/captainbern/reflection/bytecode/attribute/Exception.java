package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class Exception {

    private int startPc;
    private int endPc;
    private int handlerPc;
    private int catchType;

    public Exception(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public Exception(int startPc, int endPc, int handlerPc, int catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }

    public final int getStartPC() {
        return this.startPc;
    }

    public final void setStartPC(int startPC) {
       this.startPc = startPC;
    }

    public final int getEndCP() {
        return this.endPc;
    }

    public final void setEndPC(int endPC) {
        this.endPc = endPC;
    }

    public final int getHandlerPC() {
        return this.handlerPc;
    }

    public final void setHandlerPC(int handlerPC) {
        this.handlerPc = handlerPC;
    }

    public final int getCatchType() {
        return this.catchType;
    }

    public final void setCatchType(int catchType) {
        this.catchType = catchType;
    }
}
