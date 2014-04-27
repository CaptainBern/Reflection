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

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public class MethodHandleConstant extends Constant {

    private int ckind;
    private int cindex;

    public MethodHandleConstant(MethodHandleConstant constant) {
        this(constant.getKind(), constant.getMethodIndex());
    }

    public MethodHandleConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedByte(), stream.readUnsignedShort());
    }

    public MethodHandleConstant(int ckind, int cindex) {
        super(CONSTANT_MethodHandle);
        this.ckind = ckind;
        this.cindex = cindex;
    }

    public int getKind() {
        return this.cindex;
    }

    public int getMethodIndex() {
        return this.cindex;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeByte(this.tag);
        codeStream.writeByte(this.ckind);
        codeStream.writeShort(this.cindex);
    }
}
