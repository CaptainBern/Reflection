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

package com.captainbern.jbel.commons.attribute;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.stackmap.StackMapTableEntry;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StackMap extends Attribute implements Opcode {

    private int mapLength;
    private StackMapTableEntry[] entries;

    public StackMap(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(index, length, (StackMapTableEntry[]) null, constantPool);
        this.mapLength = codeStream.readUnsignedShort();
        for(int i = 0; i < this.mapLength; i++) {
            this.entries[i] = new StackMapTableEntry(codeStream, constantPool);
        }
    }

    public StackMap(int index, int length, StackMapTableEntry[] entries, ConstantPool constantPool) {
        super(ATTR_STACK_MAP, index, length, constantPool);
        setEntries(entries);
    }

    public int getMapSize() {
        return this.mapLength;
    }

    public StackMapTableEntry[] getEntries() {
        return this.entries;
    }

    public void setEntries(StackMapTableEntry[] entries) {
        this.entries = entries;
        this.mapLength = entries == null ? 0 : entries.length;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.mapLength);
        for(int i = 0; i < this.mapLength; i++) {
            try {
                this.entries[i].write(codeStream);
            } catch (ClassFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
