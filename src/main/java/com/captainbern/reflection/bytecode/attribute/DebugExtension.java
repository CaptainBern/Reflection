package com.captainbern.reflection.bytecode.attribute;

import java.io.DataInputStream;
import java.io.IOException;

public class DebugExtension {

    private int index;

    public DebugExtension(DebugExtension debugExtension) {
        this(debugExtension.getIndex());
    }

    public DebugExtension(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort());
    }

    public DebugExtension(int index) {
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }
}
