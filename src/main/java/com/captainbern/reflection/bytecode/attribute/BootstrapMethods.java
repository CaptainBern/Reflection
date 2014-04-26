package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class BootstrapMethods extends Attribute implements Opcode {

    private int bootstrapMethodCount;
    private BootstrapMethod[] bootstrapMethods;

    public BootstrapMethods(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (BootstrapMethod[]) null, constantPool);
        this.bootstrapMethodCount = codeStream.readUnsignedShort();
        this.bootstrapMethods = new BootstrapMethod[this.bootstrapMethodCount];
        for(int i = 0; i < this.bootstrapMethodCount; i++) {
            this.bootstrapMethods[i] = new BootstrapMethod(codeStream);
        }
    }

    public BootstrapMethods(int index, int length, BootstrapMethod[] bootstrapMethods, ConstantPool constantPool) {
        super(ATTR_BOOTSTRAP_METHODS, index, length, constantPool);
    }

    public final int getBootstrapMethodCount() {
        return this.bootstrapMethodCount;
    }

    public final BootstrapMethod[] getBootstrapMethods() {
        return this.bootstrapMethods;
    }

    public final void setBootstrapMethods(BootstrapMethod[] bootstrapMethods) {
        this.bootstrapMethodCount = bootstrapMethods == null ? 0 : bootstrapMethods.length;
        this.bootstrapMethods = bootstrapMethods;
    }
}
