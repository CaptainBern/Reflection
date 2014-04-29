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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a Deprecated attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.15">Deprecated</a>
 */
public class Deprecated extends Attribute implements Opcode {

    private byte[] bytes;

    public Deprecated(Deprecated deprecated) {
        this(deprecated.getNameIndex(), deprecated.getLength(), deprecated.getBytes(), deprecated.getConstantPool());
    }

    public Deprecated(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (byte[]) null, constantPool);
        if(length > 0) {
            this.bytes = new byte[length];
            codeStream.readFully(bytes);
        }
    }

    public Deprecated(int index, int length, byte[] bytes, ConstantPool constantPool) {
        super(ATTR_DEPRECATED, index, length, constantPool);
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        if(this.length > 0) {
            codeStream.write(this.bytes, 0, this.length);
        }
    }
}
