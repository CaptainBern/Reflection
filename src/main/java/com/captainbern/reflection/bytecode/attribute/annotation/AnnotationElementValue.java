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
import java.util.LinkedList;

public class AnnotationElementValue extends ElementValue {

    private int index;
    private ConstantPool constantPool;
    private LinkedList<ElementValuePair> members = new LinkedList<ElementValuePair>();

    private boolean visible;

    public AnnotationElementValue(AnnotationElementValue elementValue, ConstantPool constantPool) {
        super(TYPE_ANNOTATION, constantPool);
    }

    public AnnotationElementValue(int index, ConstantPool constantPool, boolean isVisible) {
        super(TYPE_ANNOTATION, constantPool);
        this.index = index;
        this.constantPool = constantPool;
        this.visible = isVisible;
    }

    public final int getIndex() {
        return this.index;
    }

    public final boolean isRuntimeVisible() {
        return this.visible;
    }

    public final int getElementValuePairsSize() {
        return this.members.size();
    }

    public final ElementValuePair[] getElementValuePairs() {
        return this.members.toArray(new ElementValuePair[this.members.size()]);
    }

    public final void addElementValue(ElementValuePair elementValuePair) {
        this.members.add(elementValuePair);
    }

    public static AnnotationElementValue read(DataInputStream codeStream, ConstantPool constantPool, boolean visible) throws IOException {
        final AnnotationElementValue annotationElementValue = new AnnotationElementValue(codeStream.readUnsignedShort(), constantPool, visible);
        final int valuePairs = codeStream.readUnsignedShort();

        for(int i = 0; i < valuePairs; i++) {
            annotationElementValue.addElementValue(new ElementValuePair(codeStream.readUnsignedShort(), ElementValue.read(codeStream, constantPool), constantPool));
        }

        return annotationElementValue;
    }
}
