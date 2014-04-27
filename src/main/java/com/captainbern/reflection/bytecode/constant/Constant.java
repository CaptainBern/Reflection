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

package com.captainbern.reflection.bytecode.constant;

import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Constant implements Opcode {

    protected byte tag;

    public Constant(final byte tag) {
        this.tag = tag;
    }

    public final byte getTag() {
        return this.tag;
    }

    public static Constant readConstant(DataInputStream codeStream) throws IOException, ClassFormatException {
        byte tag = codeStream.readByte();
        switch (tag) {
            case CONSTANT_Utf8:
                return new Utf8Constant(codeStream);
            case CONSTANT_Integer:
                return new IntegerConstant(codeStream);
            case CONSTANT_Float:
                return new FloatConstant(codeStream);
            case CONSTANT_Long:
                return new LongConstant(codeStream);
            case CONSTANT_Double:
                return new DoubleConstant(codeStream);
            case CONSTANT_Class:
                return new ClassConstant(codeStream);
            case CONSTANT_String:
                return new StringConstant(codeStream);
            case CONSTANT_Fieldref:
                return new FieldConstant(codeStream);
            case CONSTANT_Methodref:
                return new MethodConstant(codeStream);
            case CONSTANT_InterfaceMethodref:
                return new InterfaceMethodConstant(codeStream);
            case CONSTANT_NameAndType:
                return new DescriptorConstant(codeStream);
            case CONSTANT_MethodHandle:
                return new MethodHandleConstant(codeStream);
            case CONSTANT_MethodType:
                return new MethodTypeConstant(codeStream);
            case CONSTANT_InvokeDynamic:
                return new InvokeDynamicConstant(codeStream);
            default:
                throw new ClassFormatException("Invalid tag type: " + tag);
        }
    }

    public abstract void write(DataOutputStream codeStream) throws IOException;
}
