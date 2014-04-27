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

package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.attribute.stackmap.StackMapFrame;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class StackMap extends Attribute implements Opcode {

    private int mapLength;
    private StackMapFrame[] entries;

    public StackMap(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(index, length, (StackMapFrame[]) null, constantPool);
        this.mapLength = codeStream.readUnsignedShort();
        for(int i = 0; i < this.mapLength; i++) {
            this.entries[i] = new StackMapFrame(codeStream, constantPool);
        }
    }

    public StackMap(int index, int length, StackMapFrame[] entries, ConstantPool constantPool) {
        super(ATTR_STACK_MAP, index, length, constantPool);
        setEntries(entries);
    }

    public final int getMapSize() {
        return this.mapLength;
    }

    public final StackMapFrame[] getEntries() {
        return this.entries;
    }

    public final void setEntries(StackMapFrame[] entries) {
        this.entries = entries;
        this.mapLength = entries == null ? 0 : entries.length;
    }
}
