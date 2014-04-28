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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariable {

    private int startPC;
    private int length;
    private int nameIndex;
    private int signatureIndex;
    private int index;

    public LocalVariable(LocalVariable localVariable) {
        this(localVariable.getStartPC(), localVariable.getLength(), localVariable.getNameIndex(), localVariable.getSignatureIndex(), localVariable.getIndex());
    }

    public LocalVariable(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public LocalVariable(int startPC, int length, int nameIndex, int signatureIndex, int index) {
        this.startPC = startPC;
        this.length = length;
        this.nameIndex = nameIndex;
        this.signatureIndex = signatureIndex;
        this.index = index;
    }

    public final int getStartPC() {
        return this.startPC;
    }

    public final void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public final int getLength() {
        return this.length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final int getSignatureIndex() {
        return this.signatureIndex;
    }

    public final void setSignatureIndex(int signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.startPC);
        codeStream.writeShort(this.length);
        codeStream.writeShort(this.nameIndex);
        codeStream.writeShort(this.signatureIndex);
        codeStream.writeShort(this.index);
    }
}
