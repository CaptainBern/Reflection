package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class BootstrapMethod {

    private int methodIndex;
    private int numberOfArguments;
    private int[] arguments;

    public BootstrapMethod(DataInputStream codeStream) throws IOException {
        this.methodIndex = codeStream.readUnsignedShort();
        this.numberOfArguments = codeStream.readUnsignedShort();
        this.arguments = new int[this.numberOfArguments];
        for(int i = 0; i < this.numberOfArguments; i++) {
            this.arguments[i] = codeStream.readUnsignedShort();
        }
    }

    public BootstrapMethod(int index, int numberOfArguments, int[] arguments) {
        this.methodIndex = index;
        this.numberOfArguments = numberOfArguments;
        this.arguments = arguments;
    }

    public final int getMethodIndex() {
        return this.methodIndex;
    }

    public final void setMethodIndex(int methodIndex) {
        this.methodIndex = methodIndex;
    }

    public final int getNumberOfArguments() {
        return this.numberOfArguments;
    }

    public final int[] getArguments() {
        return this.arguments;
    }

    public final void setArguments(int[] arguments) {
        this.numberOfArguments = arguments == null ? 0 : arguments.length;
        this.arguments = arguments;
    }
}
