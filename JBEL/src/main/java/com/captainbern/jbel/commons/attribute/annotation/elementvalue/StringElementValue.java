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

package com.captainbern.jbel.commons.attribute.annotation.elementvalue;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringElementValue extends ElementValue {

    private int index;

    public StringElementValue(StringElementValue stringElementValue) {
        this(stringElementValue.getIndex(), stringElementValue.getConstantPool());
    }

    public StringElementValue(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(codeStream.readUnsignedShort(), constantPool);
    }

    public StringElementValue(int index, ConstantPool constantPool) {
        super(TYPE_STRING, constantPool);
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int stringIndex) {
        this.index = stringIndex;
    }

    public String getString() throws ClassFormatException {
        return getConstantPool().getUtf8(this.index).getString();
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.index);
    }
}
