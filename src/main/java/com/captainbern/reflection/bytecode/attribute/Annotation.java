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
import com.captainbern.reflection.bytecode.attribute.annotation.AnnotationEntry;

import java.io.DataInputStream;
import java.io.IOException;

public class Annotation extends Attribute {

    private AnnotationEntry[] entries;
    private boolean isRuntimeVisible;

    public Annotation(String tag, int index, int length, DataInputStream codeStream, ConstantPool constantPool, boolean isRuntimeVisible) throws IOException {
        super(tag, index, length, constantPool);
        final int tableSize = codeStream.readUnsignedShort();
        this.entries = new AnnotationEntry[tableSize];
        for(int i = 0; i < tableSize; i++) {
            this.entries[i] = AnnotationEntry.read(codeStream, constantPool, isRuntimeVisible);
        }
    }

    public Annotation(String tag, int index, int length, AnnotationEntry[] entries, ConstantPool constantPool, boolean isRuntimeVisible) {
        super(tag, index, length, constantPool);
        this.isRuntimeVisible = isRuntimeVisible;
        setEntryTable(entries);
    }

    public final void setEntryTable(AnnotationEntry[] entries) {
        this.entries = entries;
    }

    public final AnnotationEntry[] getEntryTable() {
        return this.entries;
    }

    public final boolean isRuntimeVisible() {
        return this.isRuntimeVisible;
    }
}
