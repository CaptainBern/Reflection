package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceDebugExtension extends Attribute implements Opcode {

    private int sourceDebugExtensionsCount;
    private DebugExtension[] debugExtensions;

    public SourceDebugExtension(int index, int lenght, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, lenght, (DebugExtension[]) null, constantPool);
        this.sourceDebugExtensionsCount = codeStream.readUnsignedShort();
        this.debugExtensions = new DebugExtension[this.sourceDebugExtensionsCount];
        for(int i = 0; i < this.sourceDebugExtensionsCount; i++) {
            this.debugExtensions[i] = new DebugExtension(codeStream);
        }
    }

    public SourceDebugExtension(int index, int length, DebugExtension[] debugExtensions, ConstantPool constantPool) {
        super(ATTR_SOURCE_DEBUG_EXTENSION, index, length, constantPool);
        setDebugExtensions(debugExtensions);
    }

    public final int getSourceDebugExtensionsCount() {
        return this.sourceDebugExtensionsCount;
    }

    public final DebugExtension[] getDebugExtensions() {
        return this.debugExtensions;
    }

    public final void setDebugExtensions(DebugExtension[] debugExtensions) {
        this.sourceDebugExtensionsCount = debugExtensions == null ? 0 : debugExtensions.length;
        this.debugExtensions = debugExtensions;
    }
}
