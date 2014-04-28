/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.reflection.bytecode.attribute.stackmap;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeByte(this.type);
        if(hasIndex())
            codeStream.writeShort(this.index);
    }
}
