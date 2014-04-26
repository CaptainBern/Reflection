package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class Synthetic extends Attribute implements Opcode {

    private byte[] bytes;

    public Synthetic(Synthetic synthetic) {
        this(synthetic.getNameIndex(), synthetic.getLength(), synthetic.getBytes(), synthetic.getConstantPool());
    }

    public Synthetic(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (byte[]) null, constantPool);
        if(length > 0) {
            byte[] bytes = new byte[length];
            codeStream.readFully(bytes);
        }
    }

    public Synthetic(int index, int length, byte[] bytes, ConstantPool constantPool) {
        super(ATTR_SYNTHETIC, index, length, constantPool);
        this.bytes = bytes;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
