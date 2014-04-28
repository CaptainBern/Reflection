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
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents an Exceptions attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.5">Exceptions</a>
 */
public class Exceptions extends Attribute implements Opcode {

    private int exceptionCount;
    private int[] exceptions;

    public Exceptions(Exceptions exceptions) {
        this(exceptions.getNameIndex(), exceptions.getLength(), exceptions.getExceptions(), exceptions.getConstantPool());
    }

    public Exceptions(int index, int count, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, count, (int[]) null, constantPool);
        this.exceptionCount = codeStream.readUnsignedShort();
        this.exceptions = new int[this.exceptionCount];
        for (int i = 0; i < exceptionCount; i++) {
            this.exceptions[i] = codeStream.readUnsignedShort();
        }
    }

    public Exceptions(int index, int length, int[] exceptions, ConstantPool constantPool) {
        super(ATTR_EXCEPTIONS, index, length, constantPool);
        setExceptions(exceptions);
    }

    public final int getExceptionCount() {
        return this.exceptionCount;
    }

    public final int[] getExceptions() {
        return this.exceptions;
    }

    public final void setExceptions(int[] exceptions) {
        this.exceptions = exceptions;
        this.exceptionCount = exceptions == null ? 0 : exceptions.length;
    }

    public final String[] getExceptionsAsString() throws ClassFormatException {
        String[] names = new String[this.exceptionCount];
        for(int i = 0; i < this.exceptionCount; i++) {
            names[i] = this.constantPool.getConstantString(exceptions[i], CONSTANT_Class);
        }
        return names;
    }

    @Override
    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.exceptionCount);
        for(int i = 0; i < this.exceptionCount; i++) {
            codeStream.writeShort(this.exceptions[i]);
        }
    }
}
