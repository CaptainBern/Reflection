package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class Deprecated extends Attribute implements Opcode {

    private byte[] bytes;

    public Deprecated(Deprecated deprecated) {
        this(deprecated.getNameIndex(), deprecated.getLength(), deprecated.getBytes(), deprecated.getConstantPool());
    }

    public Deprecated(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (byte[]) null, constantPool);
        if(length > 0) {
            this.bytes = new byte[length];
            codeStream.readFully(bytes);
        }
    }

    public Deprecated(int index, int length, byte[] bytes, ConstantPool constantPool) {
        super(ATTR_DEPRECATED, index, length, constantPool);
        this.bytes = bytes;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
