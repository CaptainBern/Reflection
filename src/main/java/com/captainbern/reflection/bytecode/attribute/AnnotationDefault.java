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
import com.captainbern.reflection.bytecode.attribute.annotation.elementvalue.ElementValue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents an AnnotationDefault attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.20">AnnotationDefault</a>
 */
public class AnnotationDefault extends Attribute implements Opcode {

    private ElementValue defaultValue;

    public AnnotationDefault(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (ElementValue) null, constantPool);
        this.defaultValue = ElementValue.read(codeStream, constantPool);
    }

    public AnnotationDefault(int index, int length, ElementValue defaultValue, ConstantPool constantPool) {
        super(ATTR_ANNOTATION_DEFAULT, index, length, constantPool);
        this.defaultValue = defaultValue;
    }

    public final ElementValue getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(ElementValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        this.defaultValue.write(codeStream);
    }
}
