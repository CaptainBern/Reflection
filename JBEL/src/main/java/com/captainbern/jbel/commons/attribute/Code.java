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
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a Code attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.3">Code</a>
 */
public class Code extends Attribute implements Opcode {

    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private byte[] code;
    private int exceptionTableLength;
    private Exception[] exceptionTable;
    private int attributeCount;
    private Attribute[] attributes;

    public Code(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(index, length, codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), null, null, null, constantPool);
        this.codeLength = codeStream.readInt();
        this.code = new byte[this.codeLength];

        codeStream.readFully(this.code);

        this.exceptionTableLength = codeStream.readUnsignedShort();
        this.exceptionTable = new Exception[this.exceptionTableLength];
        for (int i = 0; i < this.exceptionTableLength; i++) {
            this.exceptionTable[i] = new Exception(codeStream);
        }

        this.attributeCount = codeStream.readUnsignedShort();
        this.attributes = new Attribute[this.attributeCount];
        for (int i = 0; i < this.attributeCount; i++) {
            this.attributes[i] = Attribute.readAttribute(codeStream, constantPool);
        }

        this.length = length;
    }

    public Code(int index, int length, int maxStack, int maxLocals, byte[] code, Exception[] exceptionTable, Attribute[] attributes, ConstantPool constantPool) {
        super(ATTR_CODE, index, length, constantPool);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        setCode(code);
        setExceptionTable(exceptionTable);
        setAttributes(attributes);
    }

    private final int getInternalLength() {
        return 8 + codeLength + 2 + 8 * exceptionTableLength + 2;
    }

    private final int calculateLength() {
        int len = 0;
        for (int i = 0; i < attributeCount; i++) {
            len += attributes[i].length + 6 /*attribute header size*/;
        }
        return len + getCodeLength();
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
        this.length = calculateLength();
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public int getCodeLength() {
        return this.codeLength;
    }

    public byte[] getCode() {
        return this.code;
    }

    public void setCode(byte[] code) {
        this.codeLength = code == null ? 0 : code.length;
        this.code = code;
        this.length = calculateLength();
    }

    public int getExceptionTableLength() {
        return this.exceptionTableLength;
    }

    public Exception[] getExceptionTable() {
        return this.exceptionTable;
    }

    public void setExceptionTable(Exception[] exceptionTable) {
        this.exceptionTableLength = exceptionTable == null ? 0 : exceptionTable.length;
        this.exceptionTable = exceptionTable;
        this.length = calculateLength();
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public void setAttributeCount(int attributeCount) {
        this.attributeCount = attributeCount;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributeCount = attributes == null ? 0 : attributes.length;
        this.attributes = attributes;
        this.length = calculateLength();
    }

    public LineNumberTable getLineNumberTable() {
        for (int i = 0; i < attributeCount; i++) {
            if (attributes[i] instanceof LineNumberTable) {
                return (LineNumberTable) attributes[i];
            }
        }
        return null;
    }

    public LocalVariableTable getLocalVariableTable() {
        for (int i = 0; i < attributeCount; i++) {
            if (attributes[i] instanceof LocalVariableTable) {
                return (LocalVariableTable) attributes[i];
            }
        }
        return null;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);

        codeStream.writeShort(this.maxStack);
        codeStream.writeShort(this.maxLocals);
        codeStream.writeInt(this.codeLength);
        codeStream.write(this.code, 0, this.codeLength);

        codeStream.writeShort(this.exceptionTableLength);
        for (int i = 0; i < this.exceptionTableLength; i++) {
            this.exceptionTable[i].write(codeStream);
        }

        codeStream.writeShort(this.attributeCount);
        for (int i = 0; i < this.attributeCount; i++) {
            this.attributes[i].write(codeStream);
        }
    }
}
