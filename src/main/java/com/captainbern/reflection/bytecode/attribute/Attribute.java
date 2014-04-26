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
import com.captainbern.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

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

    public final String getTag() {
        return this.tag;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final int getLength() {
        return this.length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public String getName() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.nameIndex, CONSTANT_Utf8 );
        return constant.getString();
    }

    public static Attribute readAttribute(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        int index = codeStream.readUnsignedShort();
        Utf8Constant constant = (Utf8Constant) constantPool.getConstant(index, CONSTANT_Utf8);
        String tag = constant.getString();

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
             /**
              * case ATTR_PMG:
              * ?
              */
             case ATTR_SIGNATURE:
                 return new Signature(index, length, codeStream, constantPool);
             /**
              * case ATTR_STACKMAP
              */
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
             /**
              * case ATTR_STACK_MAP_TABLE
              */
         }

        return null;
    }
}
