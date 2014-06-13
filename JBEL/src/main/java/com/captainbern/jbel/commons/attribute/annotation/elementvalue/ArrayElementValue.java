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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ArrayElementValue extends ElementValue {

    private ElementValue[] values;

    public ArrayElementValue(ArrayElementValue arrayElementValue) {
        this(arrayElementValue.getValues(), arrayElementValue.getConstantPool());
    }

    public ArrayElementValue(ElementValue[] elementValues, ConstantPool constantPool) {
        super(TYPE_ARRAY, constantPool);
        this.values = elementValues;
    }

    public static ArrayElementValue read(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        final int size = codeStream.readUnsignedShort();
        ElementValue[] array = new ElementValue[size];
        for (int i = 0; i < size; i++) {
            array[i] = ElementValue.read(codeStream, constantPool);
        }
        return new ArrayElementValue(array, constantPool);
    }

    public int size() {
        return this.values.length;
    }

    public ElementValue[] getValues() {
        return this.values;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.values.length);
        for (int i = 0; i < this.values.length; i++) {
            this.values[i].write(codeStream);
        }
    }
}
