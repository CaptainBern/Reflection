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

import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValuePair {

    private int nameIndex;
    private ElementValue elementValue;
    private ConstantPool constantPool;

    public ElementValuePair(int nameIndex, ElementValue elementValue, ConstantPool constantPool) {
        this.nameIndex = nameIndex;
        this.elementValue = elementValue;
        this.constantPool = constantPool;
    }

    public ElementValue getElementValue() {
        return this.elementValue;
    }

    public void setElementValue(ElementValue elementValue) {
        this.elementValue = elementValue;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public String getName() throws ClassFormatException {
        return this.constantPool.getUtf8(this.nameIndex).getString();
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.nameIndex);
        this.elementValue.write(codeStream);
    }
}

