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

package com.captainbern.jbel;

import com.captainbern.jbel.commons.constant.*;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConstantPool implements Opcode {

    private int size;
    private Constant[] constantPool;

    public ConstantPool(DataInputStream codeStream) throws IOException, ClassFormatException {
        read(codeStream);
    }

    protected void read(DataInputStream inputStream) throws IOException, ClassFormatException {
        byte tag;
        this.size = inputStream.readUnsignedShort();
        this.constantPool = new Constant[this.size];

        for (int i = 1; i < this.size; i++) {
            this.constantPool[i] = Constant.readConstant(inputStream);
            tag = constantPool[i].getTag();
            if ((tag == CONSTANT_Double) || (tag == CONSTANT_Long)) {
                i++;
            }
        }
    }

    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.size);
        for (int i = 1; i < this.size; i++) {
            if (this.constantPool[i] != null) {
                this.constantPool[i].write(codeStream);
            }
        }
    }

    public Constant[] getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(Constant[] constantPool) {
        this.constantPool = constantPool;
        this.size = this.constantPool == null ? 0 : this.constantPool.length;
    }

    public int getSize() {
        return this.size;
    }

    public void setConstant(int index, Constant constant) {
        this.constantPool[index] = constant;
    }

    public byte getTag(int index) {
        return getConstant(index).getTag();
    }

    public Constant getConstant(int index) {
        if (index >= constantPool.length || index < 0) {
            throw new IndexOutOfBoundsException("Pool size: \'" + this.constantPool.length + "\'. Referenced index: \'" + index + "\'");
        }
        return constantPool[index];
    }

    public Constant getConstant(int index, byte tag) throws ClassFormatException {
        Constant constant;
        constant = getConstant(index);
        if (constant == null) {
            throw new ClassFormatException("Constant pool at index \'" + index + "\' is NULL.");
        }
        if (constant.getTag() != tag) {
            throw new ClassFormatException("Expected class \'" + CONSTANT_NAMES[tag] + "\' at index \'" + index + "\' and got \'" + constant + "\'");
        }
        return constant;
    }

    public ClassConstant getClass(int index) throws ClassFormatException {
        return (ClassConstant) getConstant(index, CONSTANT_Class);
    }

    public void setClass(int index, ClassConstant classConstant) {
        this.setConstant(index, classConstant);
    }

    public DescriptorConstant getDescriptor(int index) throws ClassFormatException {
        return (DescriptorConstant) getConstant(index, CONSTANT_NameAndType);
    }

    public void setDescriptor(int index, DescriptorConstant constant) {
        this.setConstant(index, constant);
    }

    public DoubleConstant getDouble(int index) throws ClassFormatException {
        return (DoubleConstant) getConstant(index, CONSTANT_Double);
    }

    public void setDouble(int index, DoubleConstant constant) {
        this.setConstant(index, constant);
    }

    public FloatConstant getFloat(int index) throws ClassFormatException {
        return (FloatConstant) getConstant(index, CONSTANT_Float);
    }

    public void setFloat(int index, FloatConstant constant) {
        this.setConstant(index, constant);
    }

    public IntegerConstant getInteger(int index) throws ClassFormatException {
        return (IntegerConstant) getConstant(index, CONSTANT_Integer);
    }

    public void setInteger(int index, IntegerConstant constant) {
        this.setConstant(index, constant);
    }

    public LongConstant getLong(int index) throws ClassFormatException {
        return (LongConstant) getConstant(index, CONSTANT_Long);
    }

    public void setLong(int index, LongConstant constant) {
        this.setConstant(index, constant);
    }

    public FieldConstant getFieldref(int index) throws ClassFormatException {
        return (FieldConstant) getConstant(index, CONSTANT_Fieldref);
    }

    public void setFieldref(int index, FieldConstant constant) {
        this.setConstant(index, constant);
    }

    public MethodConstant getMethodref(int index) throws ClassFormatException {
        return (MethodConstant) getConstant(index, CONSTANT_Methodref);
    }

    public void setMethodref(int index, MethodConstant constant) {
        this.setConstant(index, constant);
    }

    public MethodHandleConstant getMethodHandle(int index) throws ClassFormatException {
        return (MethodHandleConstant) getConstant(index, CONSTANT_MethodHandle);
    }

    public void setMethodHandle(int index, MethodHandleConstant constant) {
        this.setConstant(index, constant);
    }

    public MethodTypeConstant getMethodType(int index) throws ClassFormatException {
        return (MethodTypeConstant) getConstant(index, CONSTANT_MethodType);
    }

    public void setMethodType(int index, MethodTypeConstant constant) {
        this.setConstant(index, constant);
    }

    public InterfaceMethodConstant getInterfaceMethodref(int index) throws ClassFormatException {
        return (InterfaceMethodConstant) getConstant(index, CONSTANT_InterfaceMethodref);
    }

    public void setInterfaceMethodref(int index, InterfaceMethodConstant constant) {
        this.setConstant(index, constant);
    }

    public InvokeDynamicConstant getInvokeDynamic(int index) throws ClassFormatException {
        return (InvokeDynamicConstant) getConstant(index, CONSTANT_InvokeDynamic);
    }

    public void setInvokeDynamic(int index, InvokeDynamicConstant constant) {
        this.setConstant(index, constant);
    }

    public StringConstant getString(int index) throws ClassFormatException {
        return (StringConstant) getConstant(index, CONSTANT_String);
    }

    public void setString(int index, StringConstant constant) {
        this.setConstant(index, constant);
    }

    public Utf8Constant getUtf8(int index) throws ClassFormatException {
        return (Utf8Constant) getConstant(index, CONSTANT_Utf8);
    }

    public void setUtf8(int index, Utf8Constant constant) {
        this.setConstant(index, constant);
    }
}
