package com.captainbern.reflection.bytecode.attribute.stackmap;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public final class TypeInfo implements Opcode {

    private byte type;
    private int index = -1;
    private ConstantPool constantPool;

    public TypeInfo(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readByte(), -1, constantPool);
        if(hasIndex())
            setIndex(codeStream.readShort());
        setConstantPool(constantPool);
    }

    public TypeInfo(byte type, int index, ConstantPool constantPool) {
        this.type = type;
        this.index = index;
        this.constantPool = constantPool;
    }

    public final byte getType() {
        return this.type;
    }

    public final void setType(byte type) {
        if(type < ITEM_Top || type > ITEM_Uninitialized)
            throw new IllegalArgumentException("Illegal StackMapType for type: " + type);

        this.type = type;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    /**
     * Some types also have an index.
     *
     * @return
     * @see <a href="http://puu.sh/8pCiv.png">StackMapTypes</a>
     */
    public final boolean hasIndex() {
        return type == ITEM_Object || type == ITEM_Uninitialized;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}
