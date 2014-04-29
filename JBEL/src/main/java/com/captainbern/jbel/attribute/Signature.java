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

package com.captainbern.jbel.attribute;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.constant.Utf8Constant;
import com.captainbern.jbel.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a Signature attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.9">Signature</a>
 */
public class Signature extends Attribute {

    private int signatureIndex;

    public Signature(Signature signature) {
        this(signature.getNameIndex(), signature.getLength(), signature.getSignatureIndex(), signature.getConstantPool());
    }

    public Signature(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, codeStream.readUnsignedShort(), constantPool);
    }

    public Signature(int index, int length, int signatureIndex, ConstantPool constantPool) {
        super(ATTR_SIGNATURE, index, length, constantPool);
        this.signatureIndex = signatureIndex;
    }

    public final int getSignatureIndex() {
        return this.signatureIndex;
    }

    public final void setSignatureIndex(int signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    public String getSignature() throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) this.constantPool.getConstant(this.signatureIndex, CONSTANT_Utf8);
        return constant.getString();
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.signatureIndex);
    }
}
