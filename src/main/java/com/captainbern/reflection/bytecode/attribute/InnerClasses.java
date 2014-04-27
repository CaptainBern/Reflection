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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents an InnerClasses attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.6">InnerClasses</a>
 */
public class InnerClasses extends Attribute implements Opcode {

    private int numberOfInnerClasses;
    private InnerClass[] innerClasses;

    public InnerClasses(InnerClasses innerClasses) {
        this(innerClasses.getNameIndex(), innerClasses.getLength(), innerClasses.getInnerClasses(), innerClasses.getConstantPool());
    }

    public InnerClasses(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (InnerClass[]) null, constantPool);
        this.numberOfInnerClasses = codeStream.readUnsignedShort();
        this.innerClasses = new InnerClass[this.numberOfInnerClasses];
        for(int i = 0; i < this.numberOfInnerClasses; i++) {
            this.innerClasses[i] = new InnerClass(codeStream);
        }
    }

    public InnerClasses(int index, int length, InnerClass[] innerClasses, ConstantPool constantPool) {
        super(ATTR_INNER_CLASSES, index, length, constantPool);
        setInnerClasses(innerClasses);
    }

    public final int getNumberOfInnerClasses() {
        return this.numberOfInnerClasses;
    }

    public final InnerClass[] getInnerClasses() {
        return this.innerClasses;
    }

    public final void setInnerClasses(InnerClass[] innerClasses) {
        this.numberOfInnerClasses = innerClasses == null ? 0 : innerClasses.length;
        this.innerClasses = innerClasses;
    }
}
