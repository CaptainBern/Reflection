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

package com.captainbern.jbel.commons.attribute;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.constant.Utf8Constant;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Attribute implements Opcode {

    protected String tag;

    protected int nameIndex;
    protected int length;
    protected ConstantPool constantPool;

    public Attribute(String tag, int index, int length, ConstantPool constantPool) {
        this.tag = tag;
        this.nameIndex = index;
        this.length = length;
        this.constantPool = constantPool;
    }

    public static Attribute readAttribute(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        int index = codeStream.readUnsignedShort();
        String tag = constantPool.getUtf8(index).getString();
        int length = codeStream.readInt();

        switch (tag) {
            case ATTR_SOURCE_FILE:
                return new SourceFile(index, length, codeStream, constantPool);
            case ATTR_SOURCE_DEBUG_EXTENSION:
                return new SourceDebugExtension(index, length, codeStream, constantPool);
            case ATTR_CONSTANT_VALUE:
                return new ConstantValue(index, length, codeStream, constantPool);
            case ATTR_CODE:
                return new Code(index, length, codeStream, constantPool);
            case ATTR_EXCEPTIONS:
                return new Exceptions(index, length, codeStream, constantPool);
            case ATTR_LINE_NUMBER_TABLE:
                return new LineNumberTable(index, length, codeStream, constantPool);
            case ATTR_LOCAL_VARIABLE_TABLE:
                return new LocalVariableTable(index, length, codeStream, constantPool);
            case ATTR_INNER_CLASSES:
                return new InnerClasses(index, length, codeStream, constantPool);
            case ATTR_SYNTHETIC:
                return new Synthetic(index, length, codeStream, constantPool);
            case ATTR_DEPRECATED:
                return new Deprecated(index, length, codeStream, constantPool);
            case ATTR_SIGNATURE:
                return new Signature(index, length, codeStream, constantPool);
            case ATTR_STACK_MAP:
                return new StackMap(index, length, codeStream, constantPool);
            case ATTR_RUNTIME_VISIBLE_ANNOTATIONS:
                return new RuntimeVisibleAnnotations(index, length, codeStream, constantPool, true);
            case ATTR_RUNTIME_INVISIBLE_ANNOTATIONS:
                return new RuntimeInvisibleAnnotations(index, length, codeStream, constantPool, true);
            case ATTR_RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
                return new RuntimeInvisibleParameterAnnotations(index, length, codeStream, constantPool);
            case ATTR_RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
                return new RuntimeInvisibleParameterAnnotations(index, length, codeStream, constantPool);
            case ATTR_ANNOTATION_DEFAULT:
                return new AnnotationDefault(index, length, codeStream, constantPool);
            case ATTR_LOCAL_VARIABLE_TYPE_TABLE:
                return new LocalVariableTypeTable(index, length, codeStream, constantPool);
            case ATTR_ENCLOSING_METHOD:
                return new EnclosingMethod(index, length, codeStream, constantPool);
            case ATTR_BOOTSTRAP_METHODS:
                return new BootstrapMethods(index, length, codeStream, constantPool);
            case ATTR_STACK_MAP_TABLE:
                return new StackMapTable(index, length, codeStream, constantPool);
            case ATTR_METHOD_PARAMETERS:
                return new MethodParameters(index, length, codeStream, constantPool);
            default:
                throw new ClassFormatException("Unknown attribute type: " + tag);
        }
    }

    public static Attribute getAttribute(ArrayList<Attribute> attributes, String name) {
        if (attributes == null) {
            return null;
        }

        ListIterator<Attribute> iterator = attributes.listIterator();
        while (iterator.hasNext()) {
            Attribute attribute = iterator.next();

            try {
                if (attribute.getName().equals(name)) {
                    return attribute;
                }
            } catch (ClassFormatException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getTag() {
        return this.tag;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() throws ClassFormatException {
        Utf8Constant constant = this.constantPool.getUtf8(this.nameIndex);
        return constant.getString();
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.nameIndex);
        codeStream.writeInt(this.length);
    }
}
