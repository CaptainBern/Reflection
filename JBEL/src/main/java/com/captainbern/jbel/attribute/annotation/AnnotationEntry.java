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

package com.captainbern.jbel.attribute.annotation;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.attribute.annotation.elementvalue.ElementValue;
import com.captainbern.jbel.attribute.annotation.elementvalue.ElementValuePair;
import com.captainbern.jbel.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class AnnotationEntry implements Opcode {

    private final int index;
    private final ConstantPool constantPool;
    private LinkedList<ElementValuePair> elementValuePairs = null;

    private final boolean runtimeVisible;

    public static AnnotationEntry read(DataInputStream codeStream, ConstantPool constantPool, boolean visible) throws IOException {
        final AnnotationEntry annotationEntry = new AnnotationEntry(codeStream.readUnsignedShort(), constantPool, visible);
        annotationEntry.elementValuePairs = new LinkedList<>();
        final int valuePairs = codeStream.readUnsignedShort();

        for(int i = 0; i < valuePairs; i++) {
            annotationEntry.addElementValue(new ElementValuePair(codeStream.readUnsignedShort(), ElementValue.read(codeStream, constantPool), constantPool));
        }

        return annotationEntry;
    }

    public AnnotationEntry(int index, ConstantPool constantPool, boolean runtimeVisible) {
        this.index = index;
        this.constantPool = constantPool;
        this.runtimeVisible = runtimeVisible;
    }

    public final int getIndex() {
        return this.index;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final boolean isRuntimeVisible() {
        return this.runtimeVisible;
    }

    public String getAnnotationType() throws ClassFormatException {
        return this.constantPool.getUtf8StringConstant(this.index);
    }

    public final ElementValuePair[] getElementValuePairs() {
        return this.elementValuePairs.toArray(new ElementValuePair[this.elementValuePairs.size()]);
    }

    public final void addElementValue(ElementValuePair elementValuePair) {
        this.elementValuePairs.add(elementValuePair);
    }

    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.index);
        codeStream.writeShort(this.elementValuePairs.size());
        for(ElementValuePair pair : this.elementValuePairs) {
            pair.write(codeStream);
        }
    }
}
