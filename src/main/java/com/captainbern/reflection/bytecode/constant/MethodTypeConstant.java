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
import java.io.IOException;

public class MethodTypeConstant extends Constant {

    private int descriptor;

    public MethodTypeConstant(MethodTypeConstant constant) {
        this(constant.getDescriptor());
    }

    public MethodTypeConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public MethodTypeConstant(int descriptor) {
        super(CONSTANT_MethodType);
        this.descriptor = descriptor;
    }

    public int getDescriptor() {
        return this.descriptor;
    }
}
