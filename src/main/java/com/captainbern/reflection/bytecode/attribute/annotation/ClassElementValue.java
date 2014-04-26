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
import java.io.IOException;

public class ClassElementValue extends ElementValue {

    private int classIndex;

    public ClassElementValue(ClassElementValue classElementValue) {
        this(classElementValue.getIndex(), classElementValue.getConstantPool());
    }

    public ClassElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public ClassElementValue(int classIndex, ConstantPool constantPool) {
        super(ElementValue.TYPE_CLASS, constantPool);
        this.classIndex = classIndex;
    }

    public final int getIndex() {
        return this.classIndex;
    }

    public final void setIndex(int classIndex) {
        this.classIndex = classIndex;
    }
}
