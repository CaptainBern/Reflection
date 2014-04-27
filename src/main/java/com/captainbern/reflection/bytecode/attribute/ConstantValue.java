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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a ConstantValue attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.2">ConstantValue</a>
 */
public class ConstantValue extends Attribute {

    private int constantValueIndex;

    public ConstantValue(ConstantValue constantValue) {
        this(constantValue.getNameIndex(), constantValue.getLength(), constantValue.getConstantValueIndex(), constantValue.getConstantPool());
    }

    public ConstantValue(int nameIndex, int length, DataInputStream dataInputStream, ConstantPool constantPool) throws IOException {
        this(nameIndex, length, dataInputStream.readUnsignedShort(), constantPool);
    }

    public ConstantValue(int nameIndex, int length, int constantValueIndex, ConstantPool constantPool) {
        super(ATTR_CONSTANT_VALUE, nameIndex, length, constantPool);
        this.constantValueIndex = constantValueIndex;
    }

    public final int getConstantValueIndex() {
        return this.constantValueIndex;
    }

    public final void setConstantValueIndex(int index) {
        this.constantValueIndex = index;
    }
}

