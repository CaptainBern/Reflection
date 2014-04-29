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

package com.captainbern.jbel.commons.member;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.constant.Utf8Constant;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Used to parse a field or method. (since their bytecode structure is the same)
 */
public class Member implements Opcode {
    protected int accessFlags;
    protected int nameIndex;
    protected int descriptorIndex;
    protected int attributeCount;
    protected Attribute[] attributes;

    protected ConstantPool constantPool;

    public Member(Member member) {
        this(member.getAccessFlags(), member.getNameIndex(), member.getDescriptorIndex(), member.getAttributes(), member.getConstantPool());
    }

    public Member(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), null, constantPool);
        this.attributeCount = codeStream.readUnsignedShort();
        this.attributes = new Attribute[attributeCount];
        for(int i = 0; i < attributeCount; i++) {
            this.attributes[i] = Attribute.readAttribute(codeStream, constantPool);
        }
    }

    public Member(int accessFlags, int nameIndex, int descriptorIndex, Attribute[] attributes, ConstantPool constantPool) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        setAttributes(attributes);
        this.constantPool = constantPool;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getDescriptorIndex() {
        return this.descriptorIndex;
    }

    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributeCount = attributes == null ? 0 : attributes.length;
        this.attributes = attributes;
    }

    public String getName() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.nameIndex, CONSTANT_Utf8);
        return constant.getString();
    }

    public String getSignature() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.descriptorIndex, CONSTANT_Utf8);
        return constant.getString();
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.accessFlags);
        codeStream.writeShort(this.nameIndex);
        codeStream.writeShort(this.descriptorIndex);
        codeStream.writeShort(this.attributeCount);
        for(int i = 0; i < this.attributeCount; i++) {
            this.attributes[i].write(codeStream);
        }
    }
}
