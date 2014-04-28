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

package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParameterAnnotationEntry {

    private int tableLength;
    private AnnotationEntry[] entries;

    public ParameterAnnotationEntry(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this.tableLength = codeStream.readUnsignedShort();
        this.entries = new AnnotationEntry[this.tableLength];
        for (int i = 0; i < this.tableLength; i++) {
            // TODO isRuntimeVisible
            this.entries[i] = AnnotationEntry.read(codeStream, constantPool, false);
        }
    }

    public final int getTableLength() {
        return this.tableLength;
    }

    public final AnnotationEntry[] getAnnotations() {
        return this.entries;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.tableLength);
        for(int i = 0; i < this.tableLength; i++) {
            this.entries[i].write(codeStream);
        }
    }
}
