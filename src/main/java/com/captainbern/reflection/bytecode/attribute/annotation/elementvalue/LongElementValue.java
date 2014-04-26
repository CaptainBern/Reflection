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

package com.captainbern.reflection.bytecode.attribute.annotation.elementvalue;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class LongElementValue extends ElementValue {

    private int index;

    public LongElementValue(LongElementValue elementValue) {
        this(elementValue.getIndex(), elementValue.getConstantPool());
    }

    public LongElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public LongElementValue(int index, ConstantPool constantPool) {
        super(TYPE_LONG, constantPool);
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final long getLong() throws ClassFormatException {
        return getConstantPool().getLongConstant(this.index);
    }
}
