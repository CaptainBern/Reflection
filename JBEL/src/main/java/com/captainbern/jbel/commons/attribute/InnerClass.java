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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InnerClass {

    private int innerClassInfoIndex;
    private int outerClassInfoIndex;
    private int innerNameIndex;
    private int innerClassAccessFlags;

    public InnerClass(InnerClass innerClass) {
        this(innerClass.getInnerClassInfoIndex(), innerClass.getOuterClassInfoIndex(), innerClass.getInnerNameIndex(), innerClass.getInnerClassAccessFlags());
    }

    public InnerClass(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public InnerClass(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
        this.innerClassInfoIndex = innerClassInfoIndex;
        this.outerClassInfoIndex = outerClassInfoIndex;
        this.innerNameIndex = innerNameIndex;
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public int getInnerClassInfoIndex() {
        return this.innerClassInfoIndex;
    }

    public void setInnerClassInfoIndex(int innerClassInfoIndex) {
        this.innerClassInfoIndex = innerClassInfoIndex;
    }

    public int getOuterClassInfoIndex() {
        return this.outerClassInfoIndex;
    }

    public void setOuterClassInfoIndex(int outerClassInfoIndex) {
        this.outerClassInfoIndex = outerClassInfoIndex;
    }

    public int getInnerNameIndex() {
        return this.innerNameIndex;
    }

    public void setInnerNameIndex(int innerNameIndex) {
        this.innerNameIndex = innerNameIndex;
    }

    public int getInnerClassAccessFlags() {
        return this.innerClassAccessFlags;
    }

    public void setInnerClassAccessFlags(int innerClassAccessFlags) {
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.innerClassInfoIndex);
        codeStream.writeShort(this.outerClassInfoIndex);
        codeStream.writeShort(this.innerNameIndex);
        codeStream.writeShort(this.innerClassAccessFlags);
    }
}
